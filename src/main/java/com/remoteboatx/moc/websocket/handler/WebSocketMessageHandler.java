package com.remoteboatx.moc.websocket.handler;

import com.remoteboatx.moc.frontend.message.IncomingFrontendMessage;
import com.remoteboatx.moc.state.State;
import com.remoteboatx.moc.vrgp.message.LatencyMessage;
import com.remoteboatx.moc.vrgp.message.VrgpMessage;
import com.remoteboatx.moc.vrgp.message.handler.VrgpMessageHandler;
import com.remoteboatx.moc.vrgp.message.util.VrgpMessageUtil;
import com.remoteboatx.moc.websocket.WebSocketAction;
import com.remoteboatx.moc.websocket.WebSocketUtil;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Handler for all incoming and outgoing WebSocket messages from both vessels and MOC frontends. It replicates the
 * {@link org.springframework.web.socket.WebSocketHandler WebSocketHandler} distinguishing connections and messages by
 * {@link ConnectionType ConnectionType}, whether they are from a vessel or a frontend.
 *
 * @see org.springframework.web.socket.WebSocketHandler
 */
public class WebSocketMessageHandler extends TextWebSocketHandler {

    /**
     * Vessel connections by vessel ID.
     */
    private final Map<String, WebSocketSession> vesselConnections = new HashMap<>();

    private final VrgpMessageHandler vesselMessageHandler = new VrgpMessageHandler();

    private final FrontendWebSocketMessageHandler frontendMessageHandler = new FrontendWebSocketMessageHandler();

    /**
     * Maps open connections to their connection type.
     */
    private final Map<WebSocketSession, ConnectionType> connectionTypes = new HashMap<>();

    public WebSocketMessageHandler() {
        // Pass FrontendMessageHandler to state, so updates can be displayed.
        State.getInstance().setFrontendMessageHandler(frontendMessageHandler);

        // Periodically send latency message to all vessels.
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(() -> {
            for (WebSocketSession vesselSession : vesselConnections.values()) {
                sendMessageToVessel(vesselSession, new VrgpMessage().withTime(
                        new LatencyMessage().withSent(Calendar.getInstance().getTimeInMillis())));
            }
            // Send message immediately and then every five seconds.
        }, 0, 5, TimeUnit.SECONDS);
    }

    private static void sendMessageToVessel(WebSocketSession vesselSession, VrgpMessage message) {
        try {
            vesselSession.sendMessage(new TextMessage(message.toJson()));
        } catch (IOException e) {
            // TODO: Handle IOException.
            e.printStackTrace();
        }
    }

    /**
     * @see org.springframework.web.socket.WebSocketHandler#afterConnectionEstablished(WebSocketSession)
     */
    public void afterConnectionEstablished(WebSocketSession session, ConnectionType type) {
        connectionTypes.put(session, type);

        if (type == ConnectionType.VESSEL) {
            final String mmsi = WebSocketUtil.extractMmsiFromWebSocketSession(session);
            vesselConnections.put(mmsi, session);
            State.getInstance().addVessel(mmsi);
        } else {
            frontendMessageHandler.addFrontend(session);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        final ConnectionType type = connectionTypes.remove(session);

        if (type == ConnectionType.VESSEL) {
            final String mmsi = WebSocketUtil.extractMmsiFromWebSocketSession(session);
            vesselConnections.remove(mmsi);
            State.getInstance().removeVessel(mmsi);
        } else {
            frontendMessageHandler.removeFrontend(session);
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) {
        final String message = textMessage.getPayload();
        if (connectionTypes.get(session) == ConnectionType.VESSEL) {
            handleVesselMessage(session, message);
        } else {
            handleFrontendMessage(message);
        }
    }

    private void handleVesselMessage(WebSocketSession session, String message) {
        // Construct a single JSON reply message combining all replies to the received messages.
        final VrgpMessage reply = new VrgpMessage();

        final String mmsi = WebSocketUtil.extractMmsiFromWebSocketSession(session);
        final List<WebSocketAction> actions = vesselMessageHandler.handleMessage(mmsi, VrgpMessage.fromJson(message));
        for (WebSocketAction action : actions) {
            action.execute(session, reply);
        }

        // Send reply only if there was any reply to the single messages.
        if (!VrgpMessageUtil.isEmpty(reply)) {
            sendMessageToVessel(session, reply);
        }
    }

    private void handleFrontendMessage(String message) {
        final IncomingFrontendMessage frontendMessage = IncomingFrontendMessage.fromJson(message);

        // Forward VRGP messages from frontend to desired vessels.
        for (IncomingFrontendMessage.VesselMessagePair vesselMessagePair : frontendMessage) {
            final String vesselId = vesselMessagePair.getVesselId();
            final String vesselMessage = vesselMessagePair.getMessage();
            try {
                // TODO: Handle NPE when vessel connection is not open anymore.
                vesselConnections.get(vesselId).sendMessage(new TextMessage(vesselMessage));
            } catch (IOException e) {
                // TODO: Handle IOException.
                e.printStackTrace();
            }
        }
    }

    /**
     * Possible connection types for the MOC to handle.
     */
    public enum ConnectionType {
        VESSEL, FRONTEND
    }
}

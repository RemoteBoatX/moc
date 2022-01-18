package com.remoteboatx.moc.websocket.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.remoteboatx.moc.message.LatencyMessage;
import com.remoteboatx.moc.message.VrgpMessage;
import com.remoteboatx.moc.message.handler.VrgpMessageHandler;
import com.remoteboatx.moc.message.util.VrgpMessageUtil;
import com.remoteboatx.moc.state.State;
import com.remoteboatx.moc.websocket.WebSocketAction;
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
 * Handler for all incoming and outgoing WebSocket messages from both vessels and MOC frontends.
 * It replicates the {@link org.springframework.web.socket.WebSocketHandler WebSocketHandler}
 * distinguishing connections and message by {@link ConnectionType ConnectionType}, whether they
 * are from a vessel or a frontend.
 *
 * @see org.springframework.web.socket.WebSocketHandler
 */
public class WebSocketMessageHandler extends TextWebSocketHandler {

    /**
     * Vessel connections by vessel ID.
     */
    private final Map<String, WebSocketSession> vesselConnections = new HashMap<>();

    private final VrgpMessageHandler vesselMessageHandler = new VrgpMessageHandler();

    private final FrontendMessageHandler frontendMessageHandler = new FrontendMessageHandler();

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
                sendMessageToVessel(vesselSession, new VrgpMessage().withTime(new LatencyMessage()
                        .withSent(Calendar.getInstance().getTimeInMillis())));
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

    public void afterConnectionEstablished(WebSocketSession session, ConnectionType type) {
        connectionTypes.put(session, type);

        if (type == ConnectionType.FRONTEND) {
            frontendMessageHandler.addFrontend(session);
        } else {
            vesselConnections.put(session.getId(), session);
        }

        if (type == ConnectionType.VESSEL) {
            State.getInstance().addVessel(session.getId());
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        ConnectionType type = connectionTypes.remove(session);

        if (type == ConnectionType.FRONTEND) {
            frontendMessageHandler.removeFrontend(session);
        } else {
            vesselConnections.remove(session.getId());
        }

        if (type == ConnectionType.VESSEL) {
            State.getInstance().removeVessel(session.getId());
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) {
        // TODO: Parse in handleMessage.
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

        List<WebSocketAction> actions = vesselMessageHandler.handleMessage(session.getId(),
                VrgpMessage.fromJson(message));
        for (WebSocketAction action : actions) {
            action.execute(session, reply);
        }

        // Send reply only if there was any reply to the single messages.
        if (!VrgpMessageUtil.isEmpty(reply)) {
            sendMessageToVessel(session, reply);
        }
    }

    private void handleFrontendMessage(String message) {
        // TODO: Create FrontendMessage class that handles JSON.
        final JsonNode jsonMessage;
        try {
            jsonMessage = new ObjectMapper().readTree(message);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return;
        }

        // Forward VRGP message from frontend to desired vessel.
        jsonMessage.fieldNames().forEachRemaining(vesselId -> {
            final JsonNode vesselMessage = jsonMessage.get(vesselId);
            try {
                // TODO: Handle NPE when vessel connection is not open anymore.
                vesselConnections.get(vesselId).sendMessage(new TextMessage(vesselMessage.toString()));
            } catch (IOException e) {
                // TODO: Handle IOException.
                e.printStackTrace();
            }
        });
    }

    /**
     * Possible connection types for the MOC to handle.
     */
    public enum ConnectionType {
        VESSEL, FRONTEND
    }
}

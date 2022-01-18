package com.remoteboatx.moc.websocket.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.remoteboatx.moc.message.LatencyMessage;
import com.remoteboatx.moc.message.VrgpMessageType;
import com.remoteboatx.moc.state.State;
import com.remoteboatx.moc.websocket.WebSocketAction;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
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
                sendMessageToVessel(vesselSession, new LatencyMessage()
                        .withSent(Calendar.getInstance().getTimeInMillis()).toJson());
            }
            // Send message immediately and then every five seconds.
        }, 0, 5, TimeUnit.SECONDS);
    }

    private static void sendMessageToVessel(WebSocketSession vesselSession, JsonNode message) {
        try {
            vesselSession.sendMessage(new TextMessage(message.toString()));
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
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        // TODO: Parse in handleMessage.
        final JsonNode jsonMessage;
        try {
            jsonMessage = new ObjectMapper().readTree(message.getPayload());
        } catch (JsonProcessingException e) {
            // TODO: Handle invalid JSON.
            e.printStackTrace();
            return;
        }

        if (connectionTypes.get(session) == ConnectionType.VESSEL) {
            handleVesselMessage(session, jsonMessage);
        } else {
            handleFrontendMessage(jsonMessage);
        }
    }

    private void handleVesselMessage(WebSocketSession session, JsonNode jsonMessage) {
        // Construct a single JSON reply message combining all replies to the received messages.
        final ObjectNode jsonReply = new ObjectMapper().createObjectNode();

        jsonMessage.fieldNames().forEachRemaining(singleMessageKey -> {
            final JsonNode singleMessage = jsonMessage.get(singleMessageKey);

            try {
                final WebSocketAction action = VrgpMessageType.getByMessageKey(singleMessageKey)
                        .getMessageHandler().handleMessage(session.getId(), singleMessage.toString());
                action.execute(session, jsonReply);
            } catch (UnsupportedOperationException e) {
                // TODO: How to respond to unsupported messages and where to throw the exception?
                //  For testing only we could send error messages via the debug message.
            }
        });

        // Send reply only if there was any reply to the single messages.
        if (jsonReply.size() > 0) {
            sendMessageToVessel(session, jsonReply);
        }
    }

    private void handleFrontendMessage(JsonNode jsonMessage) {
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

package com.remoteboatx.moc.websocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.remoteboatx.moc.message.VrgpMessageType;
import com.remoteboatx.moc.state.State;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;
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
     * Lists of open connections by connection type.
     */
    private final List<WebSocketSession> vesselConnections = new ArrayList<>();

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
            for (WebSocketSession vesselSession : vesselConnections) {
                try {
                    vesselSession.sendMessage(new TextMessage(
                            String.format("{\"time\": {\"sent\": %d}}",
                                    Calendar.getInstance().getTimeInMillis())
                    ));
                } catch (IOException e) {
                    // TODO: Handle IOException.
                    e.printStackTrace();
                }
            }
            // Send message immediately and then every five seconds.
        }, 0, 5, TimeUnit.SECONDS);
    }

    public void afterConnectionEstablished(WebSocketSession session, ConnectionType type) {
        connectionTypes.put(session, type);

        if (type == ConnectionType.FRONTEND) {
            frontendMessageHandler.addFrontend(session);
        } else {
            vesselConnections.add(session);
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
            vesselConnections.remove(session);
        }

        if (type == ConnectionType.VESSEL) {
            State.getInstance().removeVessel(session.getId());
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        if (connectionTypes.get(session) == ConnectionType.VESSEL) {
            handleVesselMessage(session, message);
        } else {
            frontendMessageHandler.handleMessage(session, message);
        }
    }

    private void handleVesselMessage(WebSocketSession session, TextMessage message) {
        final JsonNode jsonMessage;
        try {
            jsonMessage = new ObjectMapper().readTree(message.getPayload());
        } catch (JsonProcessingException e) {
            // TODO: Handle invalid JSON.
            e.printStackTrace();
            return;
        }

        // Construct a single JSON reply message combining all replies to the received messages.
        final ObjectNode jsonReply = new ObjectMapper().createObjectNode();

        jsonMessage.fieldNames().forEachRemaining(singleMessageKey -> {
            final JsonNode singleMessage = jsonMessage.get(singleMessageKey);

            final JsonNode singleMessageReply;
            try {
                singleMessageReply =
                        VrgpMessageType.getByMessageKey(singleMessageKey).getMessageHandler()
                                .handleMessage(session.getId(), singleMessage);
            } catch (UnsupportedOperationException e) {
                // TODO: How to respond to unsupported messages and where to throw the exception?
                //  For testing only we could send error messages via the debug message.
                return;
            }

            if (singleMessageReply != null) {
                // Add reply to single message to combined reply message.
                singleMessageReply.fieldNames().forEachRemaining(replyMessageKey ->
                        jsonReply.set(replyMessageKey,
                                singleMessageReply.get(replyMessageKey)));
            }
        });

        // Send reply only if there was any reply to the single messages.
        if (jsonReply.size() > 0) {
            try {
                session.sendMessage(new TextMessage(jsonReply.asText()));
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

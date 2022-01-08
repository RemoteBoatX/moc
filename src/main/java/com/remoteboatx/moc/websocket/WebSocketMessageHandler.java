package com.remoteboatx.moc.websocket;

import com.remoteboatx.moc.message.VrgpMessageType;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
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
public class WebSocketMessageHandler extends TextWebSocketHandler implements FrontendWebSocketMessageHandler {

    /**
     * Lists of open connections by connection type.
     */
    private final Map<ConnectionType, List<WebSocketSession>> connections = new HashMap<>();

    /**
     * Maps open connections to their connection type.
     */
    private final Map<WebSocketSession, ConnectionType> connectionTypes = new HashMap<>();

    public WebSocketMessageHandler() {
        // Periodically send latency message to all vessels.
        Executors.newSingleThreadScheduledExecutor().scheduleWithFixedDelay(() -> {
            for (WebSocketSession vesselSession : connections.getOrDefault(ConnectionType.VESSEL,
                    Collections.emptyList())) {
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
        connections.computeIfAbsent(type, connectionType -> new ArrayList<>()).add(session);
        connectionTypes.put(session, type);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        connections.getOrDefault(connectionTypes.get(session), Collections.emptyList()).remove(session);
        connectionTypes.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        if (connectionTypes.get(session) == ConnectionType.VESSEL) {
            handleVesselMessage(session, message);
        } else {
            handleFrontendMessage(session, message);
        }
    }

    private void handleVesselMessage(WebSocketSession session, TextMessage message) {
        JSONObject jsonMessage = (JSONObject) JSONValue.parse(message.getPayload());

        // TODO: Construct JSON reply with handleMessage returning smaller JSON objects and then
        //  send one reply to the vessel after iterating over all messages.
        // Handle possibly multiple message by message key.
        for (Object jsonMessageKey : jsonMessage.keySet()) {
            String messageKey = (String) jsonMessageKey;
            Object singleMessage = jsonMessage.get(messageKey);

            try {
                VrgpMessageType.getByMessageKey(messageKey).getMessageHandler().handleMessage(session,
                        singleMessage, this);
            } catch (UnsupportedOperationException e) {
                // TODO: How to respond to unsupported messages and where to throw the exception?
                //  For testing only we could send error messages via the debug message.
            }
        }
    }

    private void handleFrontendMessage(WebSocketSession session, TextMessage message) {
    }

    @Override
    public void sendMessage(String message) {
        for (WebSocketSession frontend : connections.getOrDefault(ConnectionType.FRONTEND,
                Collections.emptyList())) {
            try {
                frontend.sendMessage(new TextMessage(message));
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

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

public class WebSocketMessageHandler extends TextWebSocketHandler implements VesselWebSocketMessageHandler,
        FrontendWebSocketMessageHandler {

    private final Map<ConnectionType, List<WebSocketSession>> connections = new HashMap<>();

    private final Map<WebSocketSession, ConnectionType> connectionTypes = new HashMap<>();

    public void afterConnectionEstablished(WebSocketSession session, ConnectionType type) {
        connections.computeIfAbsent(type, connectionType -> new ArrayList<>()).add(session);
        connectionTypes.put(session, type);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        connections.get(connectionTypes.get(session)).remove(session);
        connectionTypes.remove(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        if (connectionTypes.get(session) == ConnectionType.VESSEL) {
            handleVesselMessage(session, message);
        }

        // TODO: Handle frontend messages.
    }

    protected void handleVesselMessage(WebSocketSession session, TextMessage message) {
        JSONObject jsonMessage = (JSONObject) JSONValue.parse(message.getPayload());
        for (Iterator iterator = jsonMessage.keySet().iterator(); iterator.hasNext(); ) {
            String key = (String) iterator.next();
            Object value = jsonMessage.get(key);
            VrgpMessageType.getByMessageKey(key).getMessageHandler().handleMessage(session,
                    value, this);
        }
    }

    @Override
    public void sendMessage(String message) {
        for (WebSocketSession frontend : connections.getOrDefault(ConnectionType.FRONTEND, Collections.emptyList())) {
            try {
                frontend.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                // TODO: Error message and handling
                System.err.println("Error sending a message.");
                e.printStackTrace();
            }
        }
    }

    public enum ConnectionType {
        VESSEL, FRONTEND
    }
}

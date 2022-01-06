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
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class WebSocketMessageHandler extends TextWebSocketHandler implements VesselWebSocketMessageHandler,
        FrontendWebSocketMessageHandler {

    private final Map<ConnectionType, List<WebSocketSession>> connections = new HashMap<>();

    private final Map<WebSocketSession, ConnectionType> connectionTypes = new HashMap<>();

    private final Map<WebSocketSession, ScheduledFuture<?>> latencyTasks = new HashMap<>();

    public void afterConnectionEstablished(WebSocketSession session, ConnectionType type) {
        connections.computeIfAbsent(type, connectionType -> new ArrayList<>()).add(session);
        connectionTypes.put(session, type);

        if (type == ConnectionType.VESSEL) {
            ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
            ScheduledFuture<?> future = exec.scheduleWithFixedDelay(() -> {
                try {
                    session.sendMessage(new TextMessage(
                            String.format("{\"time\": {\"sent\": %d}}",
                                    Calendar.getInstance().getTimeInMillis())
                    ));
                } catch (IOException e) {
                    // TODO: Handle IOException.
                    e.printStackTrace();
                }
            }, 0, 5, TimeUnit.SECONDS);
            latencyTasks.put(session, future);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        connections.get(connectionTypes.get(session)).remove(session);
        connectionTypes.remove(session);
        latencyTasks.get(session).cancel(false);
        latencyTasks.remove(session);
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
        for (WebSocketSession frontend : connections.getOrDefault(ConnectionType.FRONTEND,
                Collections.emptyList())) {
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

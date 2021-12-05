package com.remoteboatx.moc;

import com.remoteboatx.moc.message.VesselInfoMessageHandler;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

public class WebSocketMessageHandler extends TextWebSocketHandler implements VesselMessageHandler,
        FrontendMessageHandler {

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
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        if (connectionTypes.get(session) == ConnectionType.VESSEL) {
            handleVesselMessage(session, message);
        }
    }

    protected void handleVesselMessage(WebSocketSession session, TextMessage message) throws Exception {
        JSONObject jsonMessage = (JSONObject) JSONValue.parse(message.getPayload());
        for (Iterator iterator = jsonMessage.keySet().iterator(); iterator.hasNext(); ) {
            String key = (String) iterator.next();
            switch (key) {
                // TODO: Enum.
                case "streams":
                    break;
                case "vessel":
                    VesselInfoMessageHandler.handleMessage(jsonMessage, this);
                    break;
                case "nmea":
                    // TODO: Implement conning.
                    break;
                case "time":
                    // TODO: Implement.
                    break;
                case "authenticate":
                    break;
                case "authentication":
                    break;
                case "guidance":
                    break;
                case "bye":
                    // TODO: Implement.
                    break;
                case "warning":
                    break;
                case "alarm":
                    break;
                case "caution":
                    break;
                case "emergency":
                    break;
                case "info":
                    break;
                case "debug":
                    break;
                default:
                    // TODO: Proper message and exception handling.
                    throw new UnsupportedOperationException("This message is not supported by the MOC in VRGP");
            }

        }
    }

    @Override
    public void sendMessage(JSONObject json) {
        for (WebSocketSession frontend : connections.getOrDefault(ConnectionType.FRONTEND, Collections.emptyList())) {
            try {
                frontend.sendMessage(new TextMessage(json.toJSONString()));
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

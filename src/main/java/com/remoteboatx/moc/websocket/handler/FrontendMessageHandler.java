package com.remoteboatx.moc.websocket.handler;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Message handler for connected frontends.
 */
public class FrontendMessageHandler {

    /**
     * Lists of open connections by connection type.
     */
    private final List<WebSocketSession> connections = new ArrayList<>();

    void addFrontend(WebSocketSession frontend) {
        connections.add(frontend);
    }

    void removeFrontend(WebSocketSession frontend) {
        connections.remove(frontend);
    }

    void handleMessage(WebSocketSession session, TextMessage message) {
    }

    /**
     * Sends a message to all connected frontends.
     *
     * @param message the already formatted message.
     */
    public void sendMessage(String message) {
        for (WebSocketSession frontend : connections) {
            try {
                frontend.sendMessage(new TextMessage(message));
            } catch (IOException e) {
                // TODO: Handle IOException.
                e.printStackTrace();
            }
        }
    }
}

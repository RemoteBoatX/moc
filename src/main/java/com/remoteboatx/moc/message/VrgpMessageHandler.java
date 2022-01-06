package com.remoteboatx.moc.message;

import com.remoteboatx.moc.websocket.FrontendWebSocketMessageHandler;
import org.springframework.web.socket.WebSocketSession;

/**
 * Interface for VRGP message handlers that handle messages of a specific type.
 */
public interface VrgpMessageHandler {

    /**
     * Handles a VRGP message of a specific type.
     *
     * @param vesselSession          the open WebSocket session with the vessel that sent the
     *                               message.
     * @param message                the message content.
     * @param frontendMessageHandler the frontend message handler to send messages to the frontends.
     */
    void handleMessage(WebSocketSession vesselSession, Object message,
                       FrontendWebSocketMessageHandler frontendMessageHandler);
}

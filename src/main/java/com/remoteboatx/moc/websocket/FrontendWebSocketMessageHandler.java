package com.remoteboatx.moc.websocket;

/**
 * Message handler for connected frontends.
 */
public interface FrontendWebSocketMessageHandler {

    /**
     * Sends a message to all connected frontends.
     *
     * @param message the already formatted message.
     */
    void sendMessage(String message);
}

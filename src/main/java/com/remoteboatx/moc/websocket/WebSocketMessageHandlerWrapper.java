package com.remoteboatx.moc.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

/**
 * Wrapper for {@link WebSocketMessageHandler}s to forward method calls from a
 * {@link WebSocketHandler} and enrich them with information about the
 * {@link com.remoteboatx.moc.websocket.WebSocketMessageHandler.ConnectionType ConnectionType}.
 */
public class WebSocketMessageHandlerWrapper implements WebSocketHandler {

    private final WebSocketMessageHandler messageHandler;

    private final WebSocketMessageHandler.ConnectionType connectionType;

    /**
     * Constructs a {@link WebSocketHandler} that uses a {@link WebSocketMessageHandler} to
     * handle message by connections of a specific
     * {@link com.remoteboatx.moc.websocket.WebSocketMessageHandler.ConnectionType ConnectionType}
     *
     * @param messageHandler the real message handler.
     * @param connectionType the connection type to be handled by this {@link WebSocketHandler}.
     */
    public WebSocketMessageHandlerWrapper(WebSocketMessageHandler messageHandler,
                                          WebSocketMessageHandler.ConnectionType connectionType) {

        this.messageHandler = messageHandler;
        this.connectionType = connectionType;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        // Pass the connection type to the message handler.
        messageHandler.afterConnectionEstablished(session, connectionType);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        messageHandler.handleMessage(session, message);
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        messageHandler.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        messageHandler.afterConnectionClosed(session, closeStatus);
    }

    @Override
    public boolean supportsPartialMessages() {
        return messageHandler.supportsPartialMessages();
    }
}

package com.remoteboatx.moc.websocket;

import com.remoteboatx.moc.vrgp.message.VrgpMessage;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

/**
 * WebSocketAction that closes a given WebSocket connection.
 */
public class WebSocketConnectionClosure implements WebSocketAction {

    private final CloseStatus status;

    /**
     * Creates a WebSocketAction that closes a given WebSocket connection normally.
     */
    public WebSocketConnectionClosure() {
        this(CloseStatus.NORMAL);
    }

    /**
     * Creates a WebSocketAction that closes a given WebSocket connection with a specific status.
     *
     * @param status the WebSocket close status.
     */
    public WebSocketConnectionClosure(@NonNull CloseStatus status) {
        this.status = status;
    }

    @Override
    public void execute(WebSocketSession session, VrgpMessage accumulatedReply) {
        try {
            session.close(status);
        } catch (IOException e) {
            // TODO: Handle IOException.
            e.printStackTrace();
        }
    }
}

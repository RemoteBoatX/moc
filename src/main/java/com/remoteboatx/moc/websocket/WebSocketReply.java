package com.remoteboatx.moc.websocket;

import com.remoteboatx.moc.message.VrgpMessage;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.WebSocketSession;

import java.util.function.Consumer;

/**
 * WebSocketAction that appends a JSON reply to an accumulated WebSocket message reply.
 */
public class WebSocketReply implements WebSocketAction {

    private final Consumer<VrgpMessage> reply;

    /**
     * Creates a WebSocketAction that appends the given JSON reply to an accumulated WebSocket
     * message reply.
     *
     * @param reply the JSON reply.
     */
    public WebSocketReply(@NonNull Consumer<VrgpMessage> reply) {
        this.reply = reply;
    }

    @Override
    public void execute(WebSocketSession session, VrgpMessage accumulatedReply) {
        // Add reply to single message to accumulated reply message.
        reply.accept(accumulatedReply);
    }
}

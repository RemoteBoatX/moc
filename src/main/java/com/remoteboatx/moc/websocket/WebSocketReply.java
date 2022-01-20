package com.remoteboatx.moc.websocket;

import com.remoteboatx.moc.vrgp.message.VrgpMessage;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.WebSocketSession;

import java.util.function.Consumer;

/**
 * WebSocketAction that manipulates a previously accumulated reply message.
 */
public class WebSocketReply implements WebSocketAction {

    private final Consumer<VrgpMessage> reply;

    /**
     * Creates a WebSocketAction that manipulates a previously accumulated reply message.
     *
     * @param reply a VrgpMessage consumer to manipulate the reply message.
     */
    public WebSocketReply(@NonNull Consumer<VrgpMessage> reply) {
        this.reply = reply;
    }

    @Override
    public void execute(WebSocketSession session, VrgpMessage accumulatedReply) {
        // Manipulate previously accumulated reply.
        reply.accept(accumulatedReply);
    }
}

package com.remoteboatx.moc.websocket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.lang.NonNull;
import org.springframework.web.socket.WebSocketSession;

/**
 * WebSocketAction that appends a JSON reply to an accumulated WebSocket message reply.
 */
public class WebSocketJsonReply implements WebSocketAction {

    private final JsonNode jsonReply;

    /**
     * Creates a WebSocketAction that appends the given JSON reply to an accumulated WebSocket
     * message reply.
     *
     * @param reply the JSON reply.
     */
    public WebSocketJsonReply(@NonNull JsonNode reply) {
        this.jsonReply = reply;
    }

    @Override
    public void execute(WebSocketSession session, ObjectNode accumulatedReply) {
        // Add reply to single message to accumulated reply message.
        jsonReply.fieldNames().forEachRemaining(replyMessageKey ->
                accumulatedReply.set(replyMessageKey, jsonReply.get(replyMessageKey)));
    }
}

package com.remoteboatx.moc.websocket;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.web.socket.WebSocketSession;

/**
 * Action to be executed in a WebSocket session.
 */
public interface WebSocketAction {

    /**
     * Empty WebSocketAction. Execution of this action does nothing.
     */
    WebSocketAction NONE = (session, accumulatedReply) -> {
    };

    /**
     * Executes the WebSocketAction in the given session and using the previously accumulated
     * JSON reply.
     *
     * @param session          the session to execute the WebSocketAction in.
     * @param accumulatedReply the previously accumulated JSON reply to a WebSocket message.
     */
    void execute(WebSocketSession session, ObjectNode accumulatedReply);
}

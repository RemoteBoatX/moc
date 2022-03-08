package com.remoteboatx.moc.websocket;

import com.remoteboatx.moc.message.vrgp.VrgpMessage;
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
     * reply message.
     *
     * @param session          the session to execute the WebSocketAction in.
     * @param accumulatedReply the previously accumulated VRGP reply message.
     */
    void execute(WebSocketSession session, VrgpMessage accumulatedReply);
}

package com.remoteboatx.moc.message.vrgp.handler;

import com.remoteboatx.moc.message.vrgp.ByeMessage;
import com.remoteboatx.moc.message.vrgp.VrgpMessage;
import com.remoteboatx.moc.websocket.WebSocketAction;
import com.remoteboatx.moc.websocket.WebSocketConnectionClosure;

import java.util.function.Function;

/**
 * Message handler for VRGP {@link ByeMessage}s.
 */
public class ByeMessageHandler implements VrgpSingleMessageHandler<ByeMessage> {

    @Override
    public WebSocketAction handleMessage(String vesselId, ByeMessage message) {
        return new WebSocketConnectionClosure();
    }

    @Override
    public Function<VrgpMessage, ByeMessage> getSingleMessage() {
        return VrgpMessage::getBye;
    }
}

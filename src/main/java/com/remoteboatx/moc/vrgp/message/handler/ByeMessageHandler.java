package com.remoteboatx.moc.vrgp.message.handler;

import com.remoteboatx.moc.vrgp.message.ByeMessage;
import com.remoteboatx.moc.vrgp.message.VrgpMessage;
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

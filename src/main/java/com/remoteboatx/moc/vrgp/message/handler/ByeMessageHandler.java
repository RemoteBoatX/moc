package com.remoteboatx.moc.vrgp.message.handler;

import com.remoteboatx.moc.vrgp.message.ByeMessage;
import com.remoteboatx.moc.websocket.WebSocketAction;
import com.remoteboatx.moc.websocket.WebSocketConnectionClosure;

public class ByeMessageHandler implements VrgpSingleMessageHandler<ByeMessage> {

    @Override
    public WebSocketAction handleMessage(String vesselId, ByeMessage message) {
        return new WebSocketConnectionClosure();
    }
}

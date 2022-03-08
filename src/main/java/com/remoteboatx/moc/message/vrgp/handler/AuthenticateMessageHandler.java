package com.remoteboatx.moc.message.vrgp.handler;

import com.remoteboatx.moc.message.vrgp.AuthenticateMessage;
import com.remoteboatx.moc.message.vrgp.VrgpMessage;
import com.remoteboatx.moc.websocket.WebSocketAction;

import java.util.function.Function;

public class AuthenticateMessageHandler implements VrgpSingleMessageHandler<AuthenticateMessage> {

    @Override
    public WebSocketAction handleMessage(String vesselId, AuthenticateMessage message) {
        // TODO: Implement.
        return WebSocketAction.NONE;
    }

    @Override
    public Function<VrgpMessage, AuthenticateMessage> getSingleMessage() {
        return VrgpMessage::getAuthenticate;
    }
}

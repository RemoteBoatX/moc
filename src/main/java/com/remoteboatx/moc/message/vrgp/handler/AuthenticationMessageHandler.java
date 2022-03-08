package com.remoteboatx.moc.message.vrgp.handler;

import com.remoteboatx.moc.message.vrgp.AuthenticationMessage;
import com.remoteboatx.moc.message.vrgp.VrgpMessage;
import com.remoteboatx.moc.websocket.WebSocketAction;

import java.util.function.Function;

public class AuthenticationMessageHandler implements VrgpSingleMessageHandler<AuthenticationMessage> {

    @Override
    public WebSocketAction handleMessage(String vesselId, AuthenticationMessage message) {
        // TODO: Implement.
        return WebSocketAction.NONE;
    }

    @Override
    public Function<VrgpMessage, AuthenticationMessage> getSingleMessage() {
        return VrgpMessage::getAuthentication;
    }
}

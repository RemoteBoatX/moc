package com.remoteboatx.moc.message.handler;

import com.remoteboatx.moc.websocket.WebSocketAction;
import com.remoteboatx.moc.websocket.WebSocketConnectionClosure;

public class ConnectionTerminationMessageHandler implements VrgpMessageHandler {

    @Override
    public WebSocketAction handleMessage(String vesselId, String message) {
        return new WebSocketConnectionClosure();
    }
}

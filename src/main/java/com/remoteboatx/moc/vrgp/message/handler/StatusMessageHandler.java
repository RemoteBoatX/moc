package com.remoteboatx.moc.vrgp.message.handler;

import com.remoteboatx.moc.vrgp.message.status.StatusMessage;
import com.remoteboatx.moc.websocket.WebSocketAction;

public class StatusMessageHandler implements VrgpSingleMessageHandler<StatusMessage> {

    @Override
    public WebSocketAction handleMessage(String vesselId, StatusMessage message) {
        return WebSocketAction.NONE;
    }
}

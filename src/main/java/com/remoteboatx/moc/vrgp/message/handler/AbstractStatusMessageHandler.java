package com.remoteboatx.moc.vrgp.message.handler;

import com.remoteboatx.moc.vrgp.message.VrgpMessage;
import com.remoteboatx.moc.vrgp.message.status.StatusMessage;
import com.remoteboatx.moc.websocket.WebSocketAction;

import java.util.function.Function;

public abstract class AbstractStatusMessageHandler implements VrgpSingleMessageHandler<StatusMessage> {

    private final StatusMessage.Type statusType;

    private final Function<VrgpMessage, StatusMessage> statusMessageGetter;

    protected AbstractStatusMessageHandler(StatusMessage.Type statusType, Function<VrgpMessage, StatusMessage> statusMessageGetter) {
        this.statusType = statusType;
        this.statusMessageGetter = statusMessageGetter;
    }

    @Override
    public WebSocketAction handleMessage(String vesselId, StatusMessage message) {
        return WebSocketAction.NONE;
    }

    @Override
    public final Function<VrgpMessage, StatusMessage> getSingleMessage() {
        return statusMessageGetter;
    }
}

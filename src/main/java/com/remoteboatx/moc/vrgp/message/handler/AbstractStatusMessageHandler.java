package com.remoteboatx.moc.vrgp.message.handler;

import com.remoteboatx.moc.state.State;
import com.remoteboatx.moc.vrgp.message.Status;
import com.remoteboatx.moc.vrgp.message.VrgpMessage;
import com.remoteboatx.moc.websocket.WebSocketAction;

import java.util.function.Function;

public abstract class AbstractStatusMessageHandler implements VrgpSingleMessageHandler<Status> {

    private final Status.Type statusType;

    private final Function<VrgpMessage, Status> statusMessageGetter;

    protected AbstractStatusMessageHandler(Status.Type statusType, Function<VrgpMessage, Status> statusMessageGetter) {
        this.statusType = statusType;
        this.statusMessageGetter = statusMessageGetter;
    }

    @Override
    public WebSocketAction handleMessage(String vesselId, Status message) {
        State.getInstance().updateStatus(vesselId, message.withType(statusType));
        return WebSocketAction.NONE;
    }

    @Override
    public final Function<VrgpMessage, Status> getSingleMessage() {
        return statusMessageGetter;
    }
}

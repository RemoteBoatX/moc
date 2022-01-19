package com.remoteboatx.moc.vrgp.message.handler;

import com.remoteboatx.moc.state.State;
import com.remoteboatx.moc.vrgp.message.stream.Conning;
import com.remoteboatx.moc.websocket.WebSocketAction;

public class ConningMessageHandler implements VrgpSingleMessageHandler<Conning> {

    @Override
    public WebSocketAction handleMessage(String vesselId, Conning message) {
        State.getInstance().updateConning(vesselId, message);
        return WebSocketAction.NONE;
    }
}

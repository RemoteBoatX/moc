package com.remoteboatx.moc.vrgp.message.handler;

import com.remoteboatx.moc.state.State;
import com.remoteboatx.moc.vrgp.message.Streams;
import com.remoteboatx.moc.vrgp.message.VrgpMessage;
import com.remoteboatx.moc.websocket.WebSocketAction;

import java.util.function.Function;

public class StreamsMessageHandler implements VrgpSingleMessageHandler<Streams> {

    @Override
    public WebSocketAction handleMessage(String vesselId, Streams message) {
        State.getInstance().updateStreams(vesselId, message);
        return WebSocketAction.NONE;
    }

    @Override
    public Function<VrgpMessage, Streams> getSingleMessage() {
        return VrgpMessage::getStreams;
    }
}

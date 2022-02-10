package com.remoteboatx.moc.vrgp.message.handler;

import com.remoteboatx.moc.state.State;
import com.remoteboatx.moc.vrgp.message.VesselInformationMessage;
import com.remoteboatx.moc.vrgp.message.VrgpMessage;
import com.remoteboatx.moc.vrgp.message.util.StreamsUtil;
import com.remoteboatx.moc.websocket.WebSocketAction;

import java.util.function.Function;

/**
 * Message handler for VRGP {@link VesselInformationMessage}s.
 */
public class VesselInformationMessageHandler implements VrgpSingleMessageHandler<VesselInformationMessage> {

    @Override
    public WebSocketAction handleMessage(String vesselId, VesselInformationMessage message) {
        // Store available streams for this vessel.
        State.getInstance().updateAvailableStreams(vesselId,
                StreamsUtil.getAvailableStreams(message.getStreams()));

        return WebSocketAction.NONE;
    }

    @Override
    public Function<VrgpMessage, VesselInformationMessage> getSingleMessage() {
        return VrgpMessage::getVessel;
    }
}

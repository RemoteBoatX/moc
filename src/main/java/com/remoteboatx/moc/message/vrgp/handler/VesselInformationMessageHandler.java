package com.remoteboatx.moc.message.vrgp.handler;

import com.remoteboatx.moc.message.vrgp.VesselInformation;
import com.remoteboatx.moc.message.vrgp.VrgpMessage;
import com.remoteboatx.moc.state.State;
import com.remoteboatx.moc.websocket.WebSocketAction;

import java.util.function.Function;

/**
 * Message handler for VRGP {@link VesselInformation}s.
 */
public class VesselInformationMessageHandler implements VrgpSingleMessageHandler<VesselInformation> {

    @Override
    public WebSocketAction handleMessage(String vesselId, VesselInformation message) {
        State.getInstance().updateVesselInformation(vesselId, message);
        return WebSocketAction.NONE;
    }

    @Override
    public Function<VrgpMessage, VesselInformation> getSingleMessage() {
        return VrgpMessage::getVessel;
    }
}

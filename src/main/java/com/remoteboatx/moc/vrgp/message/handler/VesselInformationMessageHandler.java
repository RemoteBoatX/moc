package com.remoteboatx.moc.vrgp.message.handler;

import com.remoteboatx.moc.state.State;
import com.remoteboatx.moc.vrgp.message.VesselInformation;
import com.remoteboatx.moc.vrgp.message.util.StreamsUtil;
import com.remoteboatx.moc.websocket.WebSocketAction;

/**
 * Message handler for VRGP {@link VesselInformation}s.
 */
public class VesselInformationMessageHandler implements VrgpSingleMessageHandler<VesselInformation> {

    @Override
    public WebSocketAction handleMessage(String vesselId, VesselInformation message) {
        // Store available streams for this vessel.
        State.getInstance().updateAvailableStreams(vesselId,
                StreamsUtil.getAvailableStreams(message.getStreams()));

        return WebSocketAction.NONE;
    }
}

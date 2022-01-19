package com.remoteboatx.moc.vrgp.message.handler;

import com.remoteboatx.moc.vrgp.message.VrgpMessage;
import com.remoteboatx.moc.websocket.WebSocketAction;

import java.util.ArrayList;
import java.util.List;

public class VrgpMessageHandler {

    private final VesselInformationMessageHandler vesselMessageHandler =
            new VesselInformationMessageHandler();

    private final ConningMessageHandler conningMessageHandler = new ConningMessageHandler();

    private final LatencyMessageHandler latencyMessageHandler = new LatencyMessageHandler();

    private final ByeMessageHandler byeMessageHandler = new ByeMessageHandler();

    public List<WebSocketAction> handleMessage(String vesselId, VrgpMessage message) {
        final List<WebSocketAction> result = new ArrayList<>();

        if (message.getVessel() != null) {
            result.add(vesselMessageHandler.handleMessage(vesselId, message.getVessel()));
        }
        if (message.getTime() != null) {
            result.add(latencyMessageHandler.handleMessage(vesselId, message.getTime()));
        }
        if (message.getBye() != null) {
            result.add(byeMessageHandler.handleMessage(vesselId, message.getBye()));
        }
        if (message.getConning() != null) {
            result.add(conningMessageHandler.handleMessage(vesselId, message.getConning()));
        }

        return result;
    }
}

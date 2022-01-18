package com.remoteboatx.moc.message.handler;

import com.remoteboatx.moc.message.VrgpMessage;
import com.remoteboatx.moc.websocket.WebSocketAction;

import java.util.ArrayList;
import java.util.List;

public class VrgpMessageHandler {

    private final LatencyMessageHandler latencyMessageHandler = new LatencyMessageHandler();

    private final ByeMessageHandler byeMessageHandler = new ByeMessageHandler();

    public List<WebSocketAction> handleMessage(String vesselId, VrgpMessage message) {
        final List<WebSocketAction> result = new ArrayList<>();

        if (message.getTime() != null) {
            result.add(latencyMessageHandler.handleMessage(vesselId, message.getTime()));
        }
        if (message.getBye() != null) {
            result.add(byeMessageHandler.handleMessage(vesselId, message.getBye()));
        }

        return result;
    }
}

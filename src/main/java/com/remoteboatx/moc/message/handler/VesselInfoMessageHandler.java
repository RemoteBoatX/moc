package com.remoteboatx.moc.message.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.remoteboatx.moc.websocket.WebSocketAction;

/**
 * Handler for VRGP vessel information messages.
 */
public class VesselInfoMessageHandler implements VrgpMessageHandler {

    @Override
    public WebSocketAction handleMessage(String vesselId, JsonNode jsonMessage) {
        return WebSocketAction.NONE;
    }
}

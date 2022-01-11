package com.remoteboatx.moc.message.handler;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Handler for VRGP vessel information messages.
 */
public class VesselInfoMessageHandler implements VrgpMessageHandler {

    @Override
    public JsonNode handleMessage(String vesselId, JsonNode jsonMessage) {
        return null;
    }
}

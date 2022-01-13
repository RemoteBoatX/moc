package com.remoteboatx.moc.message.handler;

import com.fasterxml.jackson.databind.JsonNode;

public class ConnectionTerminationMessageHandler implements VrgpMessageHandler {

    @Override
    public JsonNode handleMessage(String vesselId, JsonNode message) {
        return null;
    }
}

package com.remoteboatx.moc.message;

import com.fasterxml.jackson.databind.JsonNode;
import com.remoteboatx.moc.websocket.FrontendWebSocketMessageHandler;

/**
 * Handler for VRGP vessel information messages.
 */
public class VesselInfoMessageHandler implements VrgpMessageHandler {

    @Override
    public JsonNode handleMessage(JsonNode message,
                                  FrontendWebSocketMessageHandler frontendMessageHandler) {

        frontendMessageHandler.sendMessage(message.toString());
        return null;
    }
}

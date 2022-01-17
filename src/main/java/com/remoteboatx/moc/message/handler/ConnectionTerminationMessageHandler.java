package com.remoteboatx.moc.message.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.remoteboatx.moc.websocket.WebSocketAction;
import com.remoteboatx.moc.websocket.WebSocketConnectionClosure;

public class ConnectionTerminationMessageHandler implements VrgpMessageHandler {

    @Override
    public WebSocketAction handleMessage(String vesselId, JsonNode message) {
        return new WebSocketConnectionClosure();
    }
}

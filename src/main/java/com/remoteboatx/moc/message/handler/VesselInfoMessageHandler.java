package com.remoteboatx.moc.message.handler;

import com.remoteboatx.moc.websocket.WebSocketAction;

/**
 * Handler for VRGP vessel information messages.
 */
public class VesselInfoMessageHandler implements VrgpSingleMessageHandler {

    @Override
    public WebSocketAction handleMessage(String vesselId, String jsonMessage) {
        return WebSocketAction.NONE;
    }
}

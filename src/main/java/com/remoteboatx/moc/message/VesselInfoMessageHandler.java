package com.remoteboatx.moc.message;

import com.remoteboatx.moc.FrontendMessageHandler;

public class VesselInfoMessageHandler implements VrgpMessageHandler {

    @Override
    public void handleMessage(Object message, FrontendMessageHandler frontendMessageHandler) {
        frontendMessageHandler.sendMessage(message.toString());
    }
}

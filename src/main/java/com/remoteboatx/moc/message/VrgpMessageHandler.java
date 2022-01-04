package com.remoteboatx.moc.message;

import com.remoteboatx.moc.FrontendMessageHandler;

public interface VrgpMessageHandler {

    void handleMessage(Object message, FrontendMessageHandler frontendMessageHandler);
}

package com.remoteboatx.moc.vrgp.message.handler;

import com.remoteboatx.moc.vrgp.message.Status;
import com.remoteboatx.moc.vrgp.message.VrgpMessage;

public class DebugMessageHandler extends AbstractStatusMessageHandler {

    public DebugMessageHandler() {
        super(Status.Type.DEBUG, VrgpMessage::getDebug);
    }
}

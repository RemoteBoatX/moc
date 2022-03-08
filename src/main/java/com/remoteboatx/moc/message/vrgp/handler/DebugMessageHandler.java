package com.remoteboatx.moc.message.vrgp.handler;

import com.remoteboatx.moc.message.vrgp.Status;
import com.remoteboatx.moc.message.vrgp.VrgpMessage;

public class DebugMessageHandler extends AbstractStatusMessageHandler {

    public DebugMessageHandler() {
        super(Status.Type.DEBUG, VrgpMessage::getDebug);
    }
}

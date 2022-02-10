package com.remoteboatx.moc.vrgp.message.handler;

import com.remoteboatx.moc.vrgp.message.VrgpMessage;
import com.remoteboatx.moc.vrgp.message.status.StatusMessage;

public class DebugMessageHandler extends AbstractStatusMessageHandler {

    public DebugMessageHandler() {
        super(StatusMessage.Type.DEBUG, VrgpMessage::getDebug);
    }
}

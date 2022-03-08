package com.remoteboatx.moc.message.vrgp.handler;

import com.remoteboatx.moc.message.vrgp.Status;
import com.remoteboatx.moc.message.vrgp.VrgpMessage;

public class WarningMessageHandler extends AbstractStatusMessageHandler {

    public WarningMessageHandler() {
        super(Status.Type.WARNING, VrgpMessage::getWarning);
    }
}

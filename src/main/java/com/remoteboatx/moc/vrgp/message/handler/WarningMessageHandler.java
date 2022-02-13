package com.remoteboatx.moc.vrgp.message.handler;

import com.remoteboatx.moc.vrgp.message.Status;
import com.remoteboatx.moc.vrgp.message.VrgpMessage;

public class WarningMessageHandler extends AbstractStatusMessageHandler {

    public WarningMessageHandler() {
        super(Status.Type.WARNING, VrgpMessage::getWarning);
    }
}

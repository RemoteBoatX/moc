package com.remoteboatx.moc.vrgp.message.handler;

import com.remoteboatx.moc.vrgp.message.VrgpMessage;
import com.remoteboatx.moc.vrgp.message.status.StatusMessage;

public class WarningMessageHandler extends AbstractStatusMessageHandler {

    public WarningMessageHandler() {
        super(StatusMessage.Type.WARNING, VrgpMessage::getWarning);
    }
}

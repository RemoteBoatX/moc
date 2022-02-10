package com.remoteboatx.moc.vrgp.message.handler;

import com.remoteboatx.moc.vrgp.message.VrgpMessage;
import com.remoteboatx.moc.vrgp.message.status.StatusMessage;

public class CautionMessageHandler extends AbstractStatusMessageHandler {

    public CautionMessageHandler() {
        super(StatusMessage.Type.CAUTION, VrgpMessage::getCaution);
    }
}

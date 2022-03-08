package com.remoteboatx.moc.message.vrgp.handler;

import com.remoteboatx.moc.message.vrgp.Status;
import com.remoteboatx.moc.message.vrgp.VrgpMessage;

public class CautionMessageHandler extends AbstractStatusMessageHandler {

    public CautionMessageHandler() {
        super(Status.Type.CAUTION, VrgpMessage::getCaution);
    }
}

package com.remoteboatx.moc.vrgp.message.handler;

import com.remoteboatx.moc.vrgp.message.Status;
import com.remoteboatx.moc.vrgp.message.VrgpMessage;

public class CautionMessageHandler extends AbstractStatusMessageHandler {

    public CautionMessageHandler() {
        super(Status.Type.CAUTION, VrgpMessage::getCaution);
    }
}

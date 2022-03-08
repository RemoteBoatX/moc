package com.remoteboatx.moc.message.vrgp.handler;

import com.remoteboatx.moc.message.vrgp.Status;
import com.remoteboatx.moc.message.vrgp.VrgpMessage;

public class InfoMessageHandler extends AbstractStatusMessageHandler {

    public InfoMessageHandler() {
        super(Status.Type.INFO, VrgpMessage::getInfo);
    }
}

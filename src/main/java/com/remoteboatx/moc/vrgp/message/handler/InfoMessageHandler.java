package com.remoteboatx.moc.vrgp.message.handler;

import com.remoteboatx.moc.vrgp.message.Status;
import com.remoteboatx.moc.vrgp.message.VrgpMessage;

public class InfoMessageHandler extends AbstractStatusMessageHandler {

    public InfoMessageHandler() {
        super(Status.Type.INFO, VrgpMessage::getInfo);
    }
}

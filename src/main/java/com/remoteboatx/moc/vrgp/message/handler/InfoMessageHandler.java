package com.remoteboatx.moc.vrgp.message.handler;

import com.remoteboatx.moc.vrgp.message.VrgpMessage;
import com.remoteboatx.moc.vrgp.message.status.StatusMessage;

public class InfoMessageHandler extends AbstractStatusMessageHandler {

    public InfoMessageHandler() {
        super(StatusMessage.Type.INFO, VrgpMessage::getInfo);
    }
}

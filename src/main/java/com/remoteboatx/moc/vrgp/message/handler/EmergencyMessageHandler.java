package com.remoteboatx.moc.vrgp.message.handler;

import com.remoteboatx.moc.vrgp.message.VrgpMessage;
import com.remoteboatx.moc.vrgp.message.status.StatusMessage;

public class EmergencyMessageHandler extends AbstractStatusMessageHandler {

    public EmergencyMessageHandler() {
        super(StatusMessage.Type.EMERGENCY, VrgpMessage::getEmergency);
    }
}

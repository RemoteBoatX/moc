package com.remoteboatx.moc.message.vrgp.handler;

import com.remoteboatx.moc.message.vrgp.Status;
import com.remoteboatx.moc.message.vrgp.VrgpMessage;

public class EmergencyMessageHandler extends AbstractStatusMessageHandler {

    public EmergencyMessageHandler() {
        super(Status.Type.EMERGENCY, VrgpMessage::getEmergency);
    }
}

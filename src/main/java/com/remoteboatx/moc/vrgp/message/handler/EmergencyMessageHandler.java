package com.remoteboatx.moc.vrgp.message.handler;

import com.remoteboatx.moc.vrgp.message.Status;
import com.remoteboatx.moc.vrgp.message.VrgpMessage;

public class EmergencyMessageHandler extends AbstractStatusMessageHandler {

    public EmergencyMessageHandler() {
        super(Status.Type.EMERGENCY, VrgpMessage::getEmergency);
    }
}

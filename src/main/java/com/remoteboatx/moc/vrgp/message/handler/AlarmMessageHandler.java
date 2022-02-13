package com.remoteboatx.moc.vrgp.message.handler;

import com.remoteboatx.moc.vrgp.message.Status;
import com.remoteboatx.moc.vrgp.message.VrgpMessage;

public class AlarmMessageHandler extends AbstractStatusMessageHandler {

    public AlarmMessageHandler() {
        super(Status.Type.ALARM, VrgpMessage::getAlarm);
    }
}

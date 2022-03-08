package com.remoteboatx.moc.message.vrgp.handler;

import com.remoteboatx.moc.message.vrgp.Status;
import com.remoteboatx.moc.message.vrgp.VrgpMessage;

public class AlarmMessageHandler extends AbstractStatusMessageHandler {

    public AlarmMessageHandler() {
        super(Status.Type.ALARM, VrgpMessage::getAlarm);
    }
}

package com.remoteboatx.moc.vrgp.message.handler;

import com.remoteboatx.moc.vrgp.message.VrgpMessage;
import com.remoteboatx.moc.vrgp.message.status.StatusMessage;

public class AlarmMessageHandler extends AbstractStatusMessageHandler {

    public AlarmMessageHandler() {
        super(StatusMessage.Type.ALARM, VrgpMessage::getAlarm);
    }
}

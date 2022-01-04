package com.remoteboatx.moc;

import com.remoteboatx.moc.message.VesselInfoMessageHandler;
import com.remoteboatx.moc.message.VrgpMessageHandler;

enum VrgpMessageType {

    // TODO: Add message handlers for all message types.

    STREAMS("streams", null),

    VESSEL_INFO("vessel", new VesselInfoMessageHandler()),

    NMEA("nmea", null),

    LATENCY("time", null),

    AUTHENTICATE("authenticate", null),

    AUTHENTICATION("authentication", null),

    GUIDANCE("guidance", null),

    TERMINATE_CONNECTION("bye", null),

    NOTIFICATION_CAUTION("caution", null),

    NOTIFICATION_WARNING("warning", null),

    NOTIFICATION_ALARM("alarm", null),

    NOTIFICATION_EMERGENCY("emergency", null),

    NOTIFICATION_INFO("info", null),

    NOTIFICATION_DEBUG("debug", null);

    private final String messageKey;

    private final VrgpMessageHandler messageHandler;

    VrgpMessageType(String messageKey, VrgpMessageHandler messageHandler) {
        this.messageKey = messageKey;
        this.messageHandler = messageHandler;
    }

    public VrgpMessageHandler getMessageHandler() {
        return messageHandler;
    }

    static VrgpMessageType getByMessageKey(String messageKey) {
        for (VrgpMessageType messageType: values()) {
            if (messageType.messageKey.equals(messageKey)) {
                return messageType;
            }
        }

        // TODO: Proper message and exception handling.
        throw new UnsupportedOperationException("This message is not supported by the MOC in VRGP");
    }
}

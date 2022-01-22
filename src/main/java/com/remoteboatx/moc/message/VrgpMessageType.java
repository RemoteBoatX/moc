package com.remoteboatx.moc.message;

/**
 * VRGP message types that can be handled by the MOC, containing the message key and a handler
 * for each message type.
 */
public enum VrgpMessageType {

    // TODO: Add message handlers for all message types.

    STREAMS("streams", null),

    VESSEL_INFO("vessel", new VesselInfoMessageHandler()),

    NMEA("nmea", null),

    LATENCY("time", new LatencyMessageHandler()),

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

    /**
     * Finds the correct message type for a given message key.
     *
     * @param messageKey the message key from the JSON message.
     */
    public static VrgpMessageType getByMessageKey(String messageKey) {
        for (VrgpMessageType messageType : values()) {
            if (messageType.messageKey.equals(messageKey)) {
                return messageType;
            }
        }

        throw new UnsupportedOperationException(String.format("The message \"%s\" is not " +
                "supported by the MOC in VRGP", messageKey));
    }

    /**
     * Returns the message handler for this message type.
     */
    public VrgpMessageHandler getMessageHandler() {
        return messageHandler;
    }
}

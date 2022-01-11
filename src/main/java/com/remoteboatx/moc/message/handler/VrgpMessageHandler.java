package com.remoteboatx.moc.message.handler;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * Interface for VRGP message handlers that handle messages of a specific type.
 */
public interface VrgpMessageHandler {

    /**
     * Handles a VRGP message of a specific type.
     *
     * @param vesselId the internal ID of the vessel.
     * @param message  the message content.
     * @return reply message in JSON format, or {@code null}.
     */
    JsonNode handleMessage(String vesselId, JsonNode message);
}

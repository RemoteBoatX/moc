package com.remoteboatx.moc.message;

import com.fasterxml.jackson.databind.JsonNode;
import com.remoteboatx.moc.websocket.FrontendWebSocketMessageHandler;

/**
 * Interface for VRGP message handlers that handle messages of a specific type.
 */
public interface VrgpMessageHandler {

    /**
     * Handles a VRGP message of a specific type.
     *
     * @param message                the message content.
     * @param frontendMessageHandler the frontend message handler to send messages to the frontends.
     * @return reply message in JSON format, or {@code null}.
     */
    JsonNode handleMessage(JsonNode message,
                           FrontendWebSocketMessageHandler frontendMessageHandler);
}

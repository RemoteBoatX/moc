package com.remoteboatx.moc.message;

import com.fasterxml.jackson.databind.JsonNode;

public interface VrgpMessage {

    /**
     * Serializes the message to JSON.
     */
    JsonNode toJson();
}

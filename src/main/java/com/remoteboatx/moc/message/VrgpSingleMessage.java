package com.remoteboatx.moc.message;

import com.fasterxml.jackson.databind.JsonNode;

public interface VrgpSingleMessage {

    /**
     * Serializes the message to JSON.
     */
    JsonNode toJson();
}

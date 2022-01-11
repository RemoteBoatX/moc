package com.remoteboatx.moc.message;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Abstract class for VRGP messages. Subclasses of this class model VRGP messages in order to
 * serialize them to and deserialize them from JSON.
 */
public abstract class AbstractVrgpMessage {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    protected static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    /**
     * Serializes the message to JSON.
     */
    public JsonNode toJson() {
        return new ObjectMapper().valueToTree(this);
    }
}

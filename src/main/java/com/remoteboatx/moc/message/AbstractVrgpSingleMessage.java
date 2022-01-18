package com.remoteboatx.moc.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.lang.NonNull;

/**
 * Abstract class for VRGP messages. Subclasses of this class model VRGP messages in order to
 * serialize them to and deserialize them from JSON.
 * <p>
 * Implementation note: Abstract class was introduced in order to have a single
 * {@link ObjectMapper} for all message classes.
 */
public abstract class AbstractVrgpSingleMessage implements VrgpSingleMessage {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    protected static ObjectMapper getObjectMapper() {
        return objectMapper;
    }

    protected abstract @NonNull
    VrgpMessageType getMessageType();
}

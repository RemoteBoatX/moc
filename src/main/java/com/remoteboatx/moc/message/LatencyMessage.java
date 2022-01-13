package com.remoteboatx.moc.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.lang.NonNull;

/**
 * Models the VRGP latency message that includes a timestamp {@code sent} and optionally a second
 * timestamp {@code received}.
 */
public class LatencyMessage extends AbstractVrgpMessage {

    private Long sent;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long received;

    /**
     * Deserializes a JSON message to a Java object of this class.
     */
    public static LatencyMessage fromJson(JsonNode json) {
        final LatencyMessage message;
        try {
            message = getObjectMapper().treeToValue(json, LatencyMessage.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("\"time\" message was not formatted correctly.");
        }

        if (message.getSent() == null) {
            throw new IllegalArgumentException("\"sent\" must be set in \"time\" messages.");
        }

        if (message.getSent() < 0 || message.getReceived() < 0) {
            throw new IllegalArgumentException("\"sent\" and \"received\" must be positive in " +
                    "\"time\" messages.");
        }

        return message;
    }

    @Override
    public JsonNode toJson() {
        final ObjectNode result = getObjectMapper().createObjectNode();
        result.set(getMessageType().getMessageKey(), getObjectMapper().valueToTree(this));
        return result;
    }

    @Override
    protected @NonNull
    VrgpMessageType getMessageType() {
        return VrgpMessageType.LATENCY;
    }

    public Long getSent() {
        return sent;
    }

    public LatencyMessage withSent(Long sent) {
        this.sent = sent;
        return this;
    }

    public Long getReceived() {
        return received;
    }

    public boolean hasReceived() {
        return getReceived() != null;
    }

    public LatencyMessage withReceived(Long received) {
        this.received = received;
        return this;
    }
}

package com.remoteboatx.moc.message.vrgp;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON model of the VRGP latency message that includes a timestamp {@code sent} and optionally a second
 * timestamp {@code received}.
 */
public class LatencyMessage implements VrgpSingleMessage {

    @JsonProperty(required = true)
    private Long sent;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long received;

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

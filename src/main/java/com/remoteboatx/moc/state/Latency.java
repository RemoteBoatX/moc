package com.remoteboatx.moc.state;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Model of the latency between the MOC and a vessel including the latency of incoming and outgoing messages as well as
 * a round trip latency for messages.
 */
public class Latency {

    private Long incoming;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long outgoing;

    public Long getIncoming() {
        return incoming;
    }

    public Latency withIncoming(long incoming) {
        this.incoming = incoming;
        return this;
    }

    public Long getOutgoing() {
        return outgoing;
    }

    public Latency withOutgoing(long outgoing) {
        this.outgoing = outgoing;
        return this;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Long getRoundTrip() {
        if (incoming == null || outgoing == null) {
            return null;
        }

        return incoming + outgoing;
    }
}

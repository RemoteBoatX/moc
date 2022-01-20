package com.remoteboatx.moc.state;

/**
 * Model of the latency between the MOC and a vessel including the latency of incoming and outgoing messages as well as
 * a round trip latency for messages.
 */
public class Latency {

    private long incoming = -1;

    private long outgoing = -1;

    public long getIncoming() {
        return incoming;
    }

    public void setIncoming(long incoming) {
        this.incoming = incoming;
    }

    public long getOutgoing() {
        return outgoing;
    }

    public void setOutgoing(long outgoing) {
        this.outgoing = outgoing;
    }

    public long getRoundTrip() {
        if (incoming < 0 || outgoing < 0) {
            return -1;
        }

        return incoming + outgoing;
    }
}

package com.remoteboatx.moc.state;

/**
 * Models the static and dynamic information the MOC holds about a vessel.
 */
class Vessel {

    private Latency latency;

    Latency getLatency() {
        return latency;
    }

    void setLatency(Latency latency) {
        this.latency = latency;
    }
}

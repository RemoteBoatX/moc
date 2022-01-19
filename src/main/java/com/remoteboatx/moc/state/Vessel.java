package com.remoteboatx.moc.state;

import java.util.ArrayList;
import java.util.List;

/**
 * Models the static and dynamic information the MOC holds about a vessel.
 */
class Vessel {

    private Latency latency;

    private List<String> availableStreams = new ArrayList<>();

    Latency getLatency() {
        return latency;
    }

    void setLatency(Latency latency) {
        this.latency = latency;
    }

    public List<String> getAvailableStreams() {
        return availableStreams;
    }

    public void setAvailableStreams(List<String> availableStreams) {
        this.availableStreams = availableStreams;
    }
}

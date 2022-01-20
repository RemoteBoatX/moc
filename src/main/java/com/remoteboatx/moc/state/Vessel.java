package com.remoteboatx.moc.state;

import com.remoteboatx.moc.vrgp.message.stream.Conning;

import java.util.ArrayList;
import java.util.List;

/**
 * Model of the static and dynamic information the MOC holds about a vessel.
 */
class Vessel {

    private Latency latency;

    private List<String> availableStreams = new ArrayList<>();

    private Conning conning;

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

    public Conning getConning() {
        return conning;
    }

    public void setConning(Conning conning) {
        this.conning = conning;
    }
}

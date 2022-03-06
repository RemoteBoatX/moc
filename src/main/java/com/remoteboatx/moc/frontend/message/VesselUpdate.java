package com.remoteboatx.moc.frontend.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.remoteboatx.moc.state.Latency;
import com.remoteboatx.moc.vrgp.message.Status;
import com.remoteboatx.moc.vrgp.message.Streams;
import com.remoteboatx.moc.vrgp.message.VesselInformation;
import com.remoteboatx.moc.vrgp.message.stream.Conning;

import java.util.List;

public class VesselUpdate {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean connected;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Latency latency;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private VesselInformation vessel;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Streams streams;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Conning conning;

    // TODO: Discuss frontend API for status updates.

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Status> statuses;

    public Boolean getConnected() {
        return connected;
    }

    public Latency getLatency() {
        return latency;
    }

    public VesselInformation getVessel() {
        return vessel;
    }

    public Streams getStreams() {
        return streams;
    }

    public Conning getConning() {
        return conning;
    }

    public List<Status> getStatuses() {
        return statuses;
    }

    public VesselUpdate withConnected(Boolean connected) {
        this.connected = connected;
        return this;
    }

    public VesselUpdate withLatency(Latency latency) {
        this.latency = latency;
        return this;
    }

    public VesselUpdate withVesselInformation(VesselInformation vesselInformation) {
        this.vessel = vesselInformation;
        return this;
    }

    public VesselUpdate withStreams(Streams streams) {
        this.streams = streams;
        return this;
    }

    public VesselUpdate withConning(Conning conning) {
        this.conning = conning;
        return this;
    }

    public VesselUpdate withStatuses(List<Status> statuses) {
        this.statuses = statuses;
        return this;
    }
}

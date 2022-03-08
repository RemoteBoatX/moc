package com.remoteboatx.moc.message.frontend;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.remoteboatx.moc.message.vrgp.Conning;
import com.remoteboatx.moc.message.vrgp.Status;
import com.remoteboatx.moc.message.vrgp.VesselInformation;
import com.remoteboatx.moc.state.Latency;

import java.util.List;

public class VesselUpdate {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean connected;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Latency latency;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private VesselInformation vessel;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Conning conning;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Status status;

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

    public Conning getConning() {
        return conning;
    }

    public Status getStatus() {
        return status;
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

    public VesselUpdate withConning(Conning conning) {
        this.conning = conning;
        return this;
    }

    public VesselUpdate withStatus(Status status) {
        this.status = status;
        return this;
    }

    public VesselUpdate withStatuses(List<Status> statuses) {
        this.statuses = statuses;
        return this;
    }
}

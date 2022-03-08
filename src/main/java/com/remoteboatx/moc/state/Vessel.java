package com.remoteboatx.moc.state;

import com.remoteboatx.moc.frontend.message.VesselUpdate;
import com.remoteboatx.moc.vrgp.message.Status;
import com.remoteboatx.moc.vrgp.message.VesselInformation;
import com.remoteboatx.moc.vrgp.message.stream.Conning;

import java.util.ArrayList;
import java.util.List;

/**
 * Model of the static and dynamic information the MOC holds about a vessel.
 */
class Vessel {

    private final List<Status> statuses = new ArrayList<>();

    private Latency latency;

    private VesselInformation vesselInformation;

    private Conning conning;

    VesselUpdate getAsVesselUpdate() {
        return new VesselUpdate().withLatency(latency).withVesselInformation(vesselInformation).withConning(conning)
                .withStatuses(statuses);
    }

    void setLatency(Latency latency) {
        this.latency = latency;
    }

    public void setVesselInformation(VesselInformation vesselInformation) {
        this.vesselInformation = vesselInformation;
    }

    public void setConning(Conning conning) {
        this.conning = conning;
    }

    public Status getStatus(String id) {
        return statuses.stream().filter(knownStatus -> knownStatus.getId().equals(id)).findFirst().orElse(null);
    }

    public void addStatus(Status status) {
        statuses.add(status);
    }
}

package com.remoteboatx.moc.state;

import com.remoteboatx.moc.frontend.message.VesselUpdate;
import com.remoteboatx.moc.vrgp.message.VesselInformation;
import com.remoteboatx.moc.vrgp.message.stream.Conning;

import java.util.ArrayList;
import java.util.List;

/**
 * Model of the static and dynamic information the MOC holds about a vessel.
 */
class Vessel {

    private Latency latency;

    private VesselInformation vesselInformation;

    private Conning conning;

    private List<Status> status = new ArrayList<>();

    VesselUpdate getAsVesselUpdate() {
        return new VesselUpdate().withLatency(latency).withVesselInformation(vesselInformation).withConning(conning);
    }

    Latency getLatency() {
        return latency;
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

    public List<Status> getStatus() {
        return status;
    }

    public void setStatus(List<Status> status) {
        this.status = status;
    }
}

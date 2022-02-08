package com.remoteboatx.moc.frontend.message;

import java.util.HashMap;
import java.util.Map;

public class OutgoingFrontendMessage {

    private final Map<String, VesselUpdate> vessels = new HashMap<>();

    private boolean update = true;

    public Map<String, VesselUpdate> getVessels() {
        return vessels;
    }

    public OutgoingFrontendMessage withVesselUpdate(String vesselId, VesselUpdate vesselUpdate) {
        vessels.put(vesselId, vesselUpdate);
        return this;
    }

    public boolean isUpdate() {
        return update;
    }

    public OutgoingFrontendMessage asUpdate(boolean update) {
        this.update = update;
        return this;
    }
}

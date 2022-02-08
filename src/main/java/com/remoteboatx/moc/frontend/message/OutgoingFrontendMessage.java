package com.remoteboatx.moc.frontend.message;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.remoteboatx.moc.vrgp.message.util.JsonUtil;
import org.springframework.lang.NonNull;

import java.util.HashMap;
import java.util.Map;

public class OutgoingFrontendMessage {

    private final Map<String, VesselUpdate> vessels = new HashMap<>();

    private boolean update = true;

    /**
     * Serializes this OutgoingFrontendMessage to a JSON string.
     */
    @NonNull
    public String toJson() {
        final ObjectNode json = JsonUtil.getObjectMapper().createObjectNode();
        for (String vesselId : vessels.keySet()) {
            json.set(vesselId, JsonUtil.toJson(vessels.get(vesselId)));
        }
        json.put("update", update);
        return json.toString();
    }

    public OutgoingFrontendMessage withVesselUpdate(String vesselId, VesselUpdate vesselUpdate) {
        vessels.put(vesselId, vesselUpdate);
        return this;
    }

    public OutgoingFrontendMessage asUpdate(boolean update) {
        this.update = update;
        return this;
    }
}

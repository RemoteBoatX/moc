package com.remoteboatx.moc.message.vrgp;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON model of the VRGP vessel message that includes mostly static information about the vessel.
 */
public class VesselInformation implements VrgpSingleMessage {

    @JsonProperty(required = true)
    private Streams streams;

    /**
     * Returns the available streams of the vessel defined in the vessel message.
     */
    public Streams getStreams() {
        return streams;
    }
}

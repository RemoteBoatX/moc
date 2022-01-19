package com.remoteboatx.moc.vrgp.message;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VesselInformationMessage implements VrgpSingleMessage {

    @JsonProperty(required = true)
    private Streams streams;

    public Streams getStreams() {
        return streams;
    }
}

package com.remoteboatx.moc.vrgp.message.status;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.remoteboatx.moc.vrgp.message.VrgpSingleMessage;

public class StatusMessage implements VrgpSingleMessage {

    @JsonProperty(required = true)
    private String id;

    @JsonProperty(required = true)
    private String category;

    private Long raised;

    private Long cancelled;

    private Long acknowledged;

    // TODO: Additional properties.

    public enum Type {
        EMERGENCY, ALARM, WARNING, CAUTION, INFO, DEBUG
    }
}

package com.remoteboatx.moc.vrgp.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Status implements VrgpSingleMessage {

    @JsonProperty(required = true)
    private String id;

    private Status.Type type;

    @JsonProperty(required = true)
    private String category;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long raised;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long cancelled;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long acknowledged;

    // TODO: Additional properties.


    public String getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public Long getRaised() {
        return raised;
    }

    public Long getCancelled() {
        return cancelled;
    }

    public Long getAcknowledged() {
        return acknowledged;
    }

    public Status withType(Type type) {
        this.type = type;
        return this;
    }

    public Status withCancelled(Long cancelled) {
        this.cancelled = cancelled;
        return this;
    }

    public enum Type {
        EMERGENCY, ALARM, WARNING, CAUTION, INFO, DEBUG
    }
}

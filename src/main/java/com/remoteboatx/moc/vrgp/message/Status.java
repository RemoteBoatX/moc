package com.remoteboatx.moc.vrgp.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Status implements VrgpSingleMessage {

    @JsonProperty(required = true)
    private String id;

    @JsonIgnore
    private Status.Type type;

    @JsonProperty(required = true)
    private String category;

    private String msg;

    private String source;

    // TODO: Does a status message contain all previous timestamps or only the newly added one?

    private Long raised;

    private Long acknowledged;

    private Long cancelled;

    // TODO: Additional properties.

    public String getId() {
        return id;
    }

    @JsonIgnore
    public String getType() {
        return type.name().toLowerCase();
    }

    public String getCategory() {
        return category;
    }

    public String getMsg() {
        return msg;
    }

    public String getSource() {
        return source;
    }

    public Long getRaised() {
        return raised;
    }

    public Long getAcknowledged() {
        return acknowledged;
    }

    public Long getCancelled() {
        return cancelled;
    }

    public Status withType(Type type) {
        this.type = type;
        return this;
    }

    public Status withRaised(Long raised) {
        this.raised = raised;
        return this;
    }

    public Status withAcknowledged(Long acknowledged) {
        this.acknowledged = acknowledged;
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

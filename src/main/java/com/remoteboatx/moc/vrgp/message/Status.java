package com.remoteboatx.moc.vrgp.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Status implements VrgpSingleMessage {

    @JsonProperty(required = true)
    private String id;

    @JsonIgnore
    private Status.Type type;

    // TODO: Category has to be one of predefined categories complying with type.

    @JsonProperty(required = true)
    private String category;

    private String msg;

    private String source;

    // TODO: Does a status message contain all previous timestamps or only the newly added one?

    // TODO: One of the following fields must be set.

    private String raised;

    private String acknowledged;

    private String cancelled;

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

    public String getRaised() {
        return raised;
    }

    public String getAcknowledged() {
        return acknowledged;
    }

    public String getCancelled() {
        return cancelled;
    }

    public Status withType(Type type) {
        this.type = type;
        return this;
    }

    public Status withRaised(String raised) {
        this.raised = raised;
        return this;
    }

    public Status withAcknowledged(String acknowledged) {
        this.acknowledged = acknowledged;
        return this;
    }

    public Status withCancelled(String cancelled) {
        this.cancelled = cancelled;
        return this;
    }

    public enum Type {
        EMERGENCY, ALARM, WARNING, CAUTION, INFO, DEBUG
    }
}

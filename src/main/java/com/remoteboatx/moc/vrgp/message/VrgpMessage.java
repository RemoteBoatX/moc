package com.remoteboatx.moc.vrgp.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.remoteboatx.moc.vrgp.message.stream.Conning;
import com.remoteboatx.moc.vrgp.message.util.JsonUtil;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

/**
 * JSON model of a VRGP WebSocket message which can include any message(s) defined in the VRGP specification.
 */
public class VrgpMessage {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private VesselInformation vessel;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Streams streams;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Conning conning;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LatencyMessage time;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Status emergency;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Status alarm;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Status warning;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Status caution;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Status info;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Status debug;

    // TODO: Ask Robert what the bye message should include and adjust ByeMessage accordingly.
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private ByeMessage bye;

    /**
     * Deserializes a JSON string to a VrgpMessage object.
     */
    @NonNull
    public static VrgpMessage fromJson(@NonNull String json) {
        return JsonUtil.fromJson(json, VrgpMessage.class);
    }

    /**
     * Serializes this VrgpMessage to a JSON string.
     */
    @NonNull
    public String toJson() {
        return JsonUtil.toJsonString(this);
    }

    /**
     * Returns the vessel message included in this VrgpMessage, or null, if no such message was included.
     */
    @Nullable
    public VesselInformation getVessel() {
        return vessel;
    }

    /**
     * Returns the streams message included in this VrgpMessage, or null, if no such message was included.
     */
    @Nullable
    public Streams getStreams() {
        return streams;
    }

    /**
     * Returns the conning message included in this VrgpMessage, or null, if no such message was included.
     */
    @Nullable
    public Conning getConning() {
        return conning;
    }

    /**
     * Returns the time message included in this VrgpMessage, or null, if no such message was included.
     */
    @Nullable
    public LatencyMessage getTime() {
        return time;
    }

    /**
     * Adds a time message to this VrgpMessage.
     */
    public VrgpMessage withTime(@NonNull LatencyMessage latencyMessage) {
        time = latencyMessage;
        return this;
    }

    public Status getEmergency() {
        return emergency;
    }

    public Status getAlarm() {
        return alarm;
    }

    public Status getWarning() {
        return warning;
    }

    public Status getCaution() {
        return caution;
    }

    public Status getInfo() {
        return info;
    }

    public Status getDebug() {
        return debug;
    }

    /**
     * Returns the bye message included in this VrgpMessage, or null, if no such message was included.
     */
    @Nullable
    public ByeMessage getBye() {
        return bye;
    }

    /**
     * Adds a default bye message to this VrgpMessage.
     */
    public VrgpMessage withBye() {
        return withBye(new ByeMessage());
    }

    /**
     * Adds a specific bye message to this VrgpMessage.
     */
    public VrgpMessage withBye(@NonNull ByeMessage byeMessage) {
        bye = byeMessage;
        return this;
    }
}

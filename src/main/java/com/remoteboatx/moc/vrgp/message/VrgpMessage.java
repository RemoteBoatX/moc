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
    private Conning conning;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LatencyMessage time;

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

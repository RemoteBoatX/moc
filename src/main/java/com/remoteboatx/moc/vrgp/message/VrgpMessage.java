package com.remoteboatx.moc.vrgp.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.remoteboatx.moc.vrgp.message.status.StatusMessage;
import com.remoteboatx.moc.vrgp.message.stream.Conning;
import com.remoteboatx.moc.vrgp.message.util.JsonUtil;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.HashMap;
import java.util.Map;

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

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private StatusMessage emergency;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private StatusMessage alarm;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private StatusMessage warning;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private StatusMessage caution;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private StatusMessage info;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private StatusMessage debug;

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

    @JsonIgnore
    public Map<StatusMessage.Type, StatusMessage> getStatusMessages() {
        final Map<StatusMessage.Type, StatusMessage> result = new HashMap<>();
        putStatusMessageToMapIfNotNull(StatusMessage.Type.EMERGENCY, emergency, result);
        putStatusMessageToMapIfNotNull(StatusMessage.Type.ALARM, alarm, result);
        putStatusMessageToMapIfNotNull(StatusMessage.Type.WARNING, warning, result);
        putStatusMessageToMapIfNotNull(StatusMessage.Type.CAUTION, caution, result);
        putStatusMessageToMapIfNotNull(StatusMessage.Type.INFO, info, result);
        putStatusMessageToMapIfNotNull(StatusMessage.Type.DEBUG, debug, result);
        return result;
    }

    private void putStatusMessageToMapIfNotNull(StatusMessage.Type type, StatusMessage statusMessage,
                                                Map<StatusMessage.Type, StatusMessage> statusMessages) {

        if (statusMessage == null) {
            return;
        }
        statusMessages.put(type, statusMessage);
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

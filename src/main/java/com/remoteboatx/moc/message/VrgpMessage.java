package com.remoteboatx.moc.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class VrgpMessage {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private LatencyMessage time;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    // TODO: Ask Robert what the bye message should include and adjust ByeMessage accordingly.
    private ByeMessage bye;

    @NonNull
    public static VrgpMessage fromJson(@NonNull String json) {
        try {
            return objectMapper.readValue(json, VrgpMessage.class);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Message does not comply with VRGP message format.");
        }
    }

    @NonNull
    public String toJson() {
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            // TODO: Handle JsonProcessingException.
            e.printStackTrace();
            return "";
        }
    }

    @Nullable
    public LatencyMessage getTime() {
        return time;
    }

    public VrgpMessage withTime(@NonNull LatencyMessage latencyMessage) {
        time = latencyMessage;
        return this;
    }

    @Nullable
    public ByeMessage getBye() {
        return bye;
    }

    public VrgpMessage withBye() {
        return withBye(new ByeMessage());
    }

    public VrgpMessage withBye(@NonNull ByeMessage byeMessage) {
        bye = byeMessage;
        return this;
    }
}

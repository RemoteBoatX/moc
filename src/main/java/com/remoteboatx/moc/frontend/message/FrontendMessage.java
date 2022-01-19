package com.remoteboatx.moc.frontend.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.lang.NonNull;

import java.util.Iterator;
import java.util.Map;

public class FrontendMessage implements Iterable<FrontendMessage.VesselMessagePair> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final JsonNode jsonMessage;

    private FrontendMessage(JsonNode jsonMessage) {
        this.jsonMessage = jsonMessage;
    }

    @NonNull
    public static FrontendMessage fromJson(@NonNull String json) {
        try {
            return new FrontendMessage(objectMapper.readTree(json));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Message does not comply with frontend message " +
                    "format.");
        }
    }

    @Override
    public Iterator<VesselMessagePair> iterator() {
        final Iterator<Map.Entry<String, JsonNode>> jsonNodeIterator = jsonMessage.fields();
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return jsonNodeIterator.hasNext();
            }

            @Override
            public VesselMessagePair next() {
                final Map.Entry<String, JsonNode> jsonEntry = jsonNodeIterator.next();
                return new VesselMessagePair(jsonEntry.getKey(), jsonEntry.getValue().toString());
            }
        };
    }

    public static final class VesselMessagePair {

        final String vesselId;

        final String message;

        private VesselMessagePair(String vesselId, String message) {
            this.vesselId = vesselId;
            this.message = message;
        }

        public String getVesselId() {
            return vesselId;
        }

        public String getMessage() {
            return message;
        }
    }
}

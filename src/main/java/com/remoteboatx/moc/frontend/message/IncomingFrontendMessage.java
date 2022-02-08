package com.remoteboatx.moc.frontend.message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.remoteboatx.moc.vrgp.message.util.JsonUtil;
import org.springframework.lang.NonNull;

import java.util.Iterator;
import java.util.Map;

/**
 * JSON model of messages sent by a frontend which contains pairs of vessel IDs and the messages that should be sent to
 * the vessel represented by the vessel ID.
 */
public class IncomingFrontendMessage implements Iterable<IncomingFrontendMessage.VesselMessagePair> {

    private final JsonNode jsonMessage;

    private IncomingFrontendMessage(JsonNode jsonMessage) {
        this.jsonMessage = jsonMessage;
    }

    /**
     * Deserializes a JSON string to a FrontendMessage object.
     */
    @NonNull
    public static IncomingFrontendMessage fromJson(@NonNull String json) {
        try {
            return new IncomingFrontendMessage(JsonUtil.getObjectMapper().readTree(json));
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Message does not comply with frontend message format.");
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

    /**
     * A pair of a vessel ID and a message which is intended to be sent to the vessel represented by the ID.
     */
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

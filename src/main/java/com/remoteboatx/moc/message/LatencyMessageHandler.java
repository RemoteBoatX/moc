package com.remoteboatx.moc.message;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.remoteboatx.moc.websocket.FrontendWebSocketMessageHandler;

import java.util.Calendar;

/**
 * Message handler for VRGP latency messages.
 */
public class LatencyMessageHandler implements VrgpMessageHandler {

    @Override
    public JsonNode handleMessage(
            JsonNode message, FrontendWebSocketMessageHandler frontendMessageHandler) {

        if (message.has("received") && message.has("sent")) {
            return handleMessageWithSentAndReceivedTimestamp(message, frontendMessageHandler);
        } else if (message.has("sent")) {
            return handleMessageWithSentTimestamp(message, frontendMessageHandler);
        } else {
            throw new IllegalArgumentException("\"time\" message was not formatted correctly.");
        }
    }

    private JsonNode handleMessageWithSentAndReceivedTimestamp(
            JsonNode message, FrontendWebSocketMessageHandler frontendMessageHandler) {

        long now = Calendar.getInstance().getTimeInMillis();

        long sent = message.get("sent").asLong(-1);
        long received = message.get("received").asLong(-1);
        if (sent < 0 || received < 0) {
            throw new IllegalArgumentException("\"sent\" and \"received\" have to be " +
                    "numerical values in the \"time\" message.");
        }

        long latencyOutgoing = received - sent;
        long latencyIncoming = now - received;
        long latencyRoundTrip = now - sent;

        // TODO: This check does not guarantee clock synchronisation. This should be ensured
        //  elsewhere.
        //  if (latencyOutgoing < 0 || latencyIncoming < 0) {
        //      throw new IllegalStateException("The clocks of the vessel and the MOC are not
        //      " +
        //              "synchronized.");
        //  }

        // TODO: Format messages to frontend properly.
        frontendMessageHandler.sendMessage(String.format("outgoing: %d, incoming: %d, round " +
                "trip: %d", latencyOutgoing, latencyIncoming, latencyRoundTrip));

        return null;
    }

    private JsonNode handleMessageWithSentTimestamp(
            JsonNode message, FrontendWebSocketMessageHandler frontendMessageHandler) {

        long now = Calendar.getInstance().getTimeInMillis();

        long sent = message.get("sent").asLong(-1);
        if (sent < 0) {
            throw new IllegalArgumentException("\"sent\" has to be a numerical value in the " +
                    "\"time\" message.");
        }

        long latencyIncoming = now - sent;

        // TODO: This check does not guarantee clock synchronisation. This should be ensured
        //  elsewhere.
        //  if (latencyIncoming < 0) {
        //      throw new IllegalStateException("The clocks of the vessel and the MOC are not " +
        //              "synchronized.");
        //  }

        // TODO: Format messages to frontend properly.
        frontendMessageHandler.sendMessage(String.format("incoming: %d", latencyIncoming));

        // TODO: Create Java class to model time message.
        final ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper
                .createObjectNode()
                .set("time", objectMapper.createObjectNode()
                        .put("sent", sent)
                        .put("received", now));
    }
}

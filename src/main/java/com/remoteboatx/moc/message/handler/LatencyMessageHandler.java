package com.remoteboatx.moc.message.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.remoteboatx.moc.message.LatencyMessage;
import com.remoteboatx.moc.state.Latency;
import com.remoteboatx.moc.state.State;

import java.util.Calendar;

/**
 * Message handler for VRGP latency messages.
 */
public class LatencyMessageHandler implements VrgpMessageHandler {

    @Override
    public JsonNode handleMessage(String vesselId, JsonNode jsonMessage) {
        // TODO: Handle IllegalArgumentExceptions from fromJson.
        final LatencyMessage message = LatencyMessage.fromJson(jsonMessage);
        if (message.hasReceived()) {
            return handleMessageWithSentAndReceivedTimestamp(vesselId, jsonMessage);
        } else {
            return handleMessageWithSentTimestamp(vesselId, jsonMessage);
        }
    }

    private JsonNode handleMessageWithSentAndReceivedTimestamp(String vesselId, JsonNode message) {
        long now = Calendar.getInstance().getTimeInMillis();

        long sent = message.get("sent").asLong(-1);
        long received = message.get("received").asLong(-1);
        if (sent < 0 || received < 0) {
            throw new IllegalArgumentException("\"sent\" and \"received\" have to be " +
                    "numerical values in the \"time\" message.");
        }

        Latency latency = new Latency();
        latency.setOutgoing(received - sent);
        latency.setIncoming(now - received);

        // TODO: This check does not guarantee clock synchronisation. This should be ensured
        //  elsewhere.
        //  if (latencyOutgoing < 0 || latencyIncoming < 0) {
        //      throw new IllegalStateException("The clocks of the vessel and the MOC are not
        //      " +
        //              "synchronized.");
        //  }

        // TODO: Format messages to frontend properly.
        State.getInstance().updateLatency(vesselId, latency);

        return null;
    }

    private JsonNode handleMessageWithSentTimestamp(String vesselId, JsonNode message) {
        long now = Calendar.getInstance().getTimeInMillis();

        long sent = message.get("sent").asLong(-1);
        if (sent < 0) {
            throw new IllegalArgumentException("\"sent\" has to be a numerical value in the " +
                    "\"time\" message.");
        }

        // TODO: What to do if state currently holds incoming AND outgoing latency but with this
        //  update only incoming latency is known? Either outgoing stays as is or is forgotten.
        Latency latency = new Latency();
        latency.setIncoming(now - sent);

        // TODO: This check does not guarantee clock synchronisation. This should be ensured
        //  elsewhere.
        //  if (latencyIncoming < 0) {
        //      throw new IllegalStateException("The clocks of the vessel and the MOC are not " +
        //              "synchronized.");
        //  }

        State.getInstance().updateLatency(vesselId, latency);

        return new LatencyMessage().withSent(sent).withReceived(now).toJson();
    }
}

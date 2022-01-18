package com.remoteboatx.moc.message.handler;

import com.remoteboatx.moc.message.LatencyMessage;
import com.remoteboatx.moc.state.Latency;
import com.remoteboatx.moc.state.State;
import com.remoteboatx.moc.websocket.WebSocketAction;
import com.remoteboatx.moc.websocket.WebSocketJsonReply;

import java.util.Calendar;

/**
 * Message handler for VRGP latency messages.
 */
public class LatencyMessageHandler implements VrgpMessageHandler {

    @Override
    public WebSocketAction handleMessage(String vesselId, String jsonMessage) {
        // TODO: Handle IllegalArgumentExceptions from fromJson.
        final LatencyMessage message = LatencyMessage.fromJson(jsonMessage);
        if (message.hasReceived()) {
            return handleMessageWithSentAndReceivedTimestamp(vesselId, message);
        } else {
            return handleMessageWithSentTimestamp(vesselId, message);
        }
    }

    private WebSocketAction handleMessageWithSentAndReceivedTimestamp(
            String vesselId, LatencyMessage message) {

        final long now = Calendar.getInstance().getTimeInMillis();

        final long sent = message.getSent();
        final long received = message.getReceived();

        final Latency latency = new Latency();
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

        return WebSocketAction.NONE;
    }

    private WebSocketAction handleMessageWithSentTimestamp(
            String vesselId, LatencyMessage message) {

        final long now = Calendar.getInstance().getTimeInMillis();

        final long sent = message.getSent();
        if (sent < 0) {
            throw new IllegalArgumentException("\"sent\" has to be a numerical value in the " +
                    "\"time\" message.");
        }

        // TODO: What to do if state currently holds incoming AND outgoing latency but with this
        //  update only incoming latency is known? Either outgoing stays as is or is forgotten.
        final Latency latency = new Latency();
        latency.setIncoming(now - sent);

        // TODO: This check does not guarantee clock synchronisation. This should be ensured
        //  elsewhere.
        //  if (latencyIncoming < 0) {
        //      throw new IllegalStateException("The clocks of the vessel and the MOC are not " +
        //              "synchronized.");
        //  }

        State.getInstance().updateLatency(vesselId, latency);

        final LatencyMessage reply =
                new LatencyMessage().withSent(sent).withReceived(now);
        return new WebSocketJsonReply(reply.toJson());
    }
}

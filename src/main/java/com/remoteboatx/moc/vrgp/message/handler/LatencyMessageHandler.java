package com.remoteboatx.moc.vrgp.message.handler;

import com.remoteboatx.moc.state.Latency;
import com.remoteboatx.moc.state.State;
import com.remoteboatx.moc.vrgp.message.LatencyMessage;
import com.remoteboatx.moc.vrgp.message.VrgpMessage;
import com.remoteboatx.moc.websocket.WebSocketAction;
import com.remoteboatx.moc.websocket.WebSocketReply;

import java.util.Calendar;
import java.util.function.Function;

/**
 * Message handler for VRGP {@link LatencyMessage}s.
 */
public class LatencyMessageHandler implements VrgpSingleMessageHandler<LatencyMessage> {

    @Override
    public WebSocketAction handleMessage(String vesselId, LatencyMessage message) {
        if (message.hasReceived()) {
            return handleMessageWithSentAndReceivedTimestamp(vesselId, message);
        } else {
            return handleMessageWithSentTimestamp(vesselId, message);
        }
    }

    private WebSocketAction handleMessageWithSentAndReceivedTimestamp(String vesselId, LatencyMessage message) {
        final long now = Calendar.getInstance().getTimeInMillis();
        final long sent = message.getSent();
        final long received = message.getReceived();
        final Latency latency = new Latency().withOutgoing(received - sent).withIncoming(now - received);

        // TODO: This check does not guarantee clock synchronisation. This should be ensured
        //  elsewhere.
        //  if (latencyOutgoing < 0 || latencyIncoming < 0) {
        //      throw new IllegalStateException("The clocks of the vessel and the MOC are not
        //      " +
        //              "synchronized.");
        //  }

        State.getInstance().updateLatency(vesselId, latency);
        return WebSocketAction.NONE;
    }

    private WebSocketAction handleMessageWithSentTimestamp(String vesselId, LatencyMessage message) {
        final long now = Calendar.getInstance().getTimeInMillis();
        final long sent = message.getSent();

        // TODO: What to do if state currently holds incoming AND outgoing latency but with this
        //  update only incoming latency is known? Either outgoing stays as is or is forgotten.
        final Latency latency = new Latency().withIncoming(now - sent);

        // TODO: This check does not guarantee clock synchronisation. This should be ensured
        //  elsewhere.
        //  if (latencyIncoming < 0) {
        //      throw new IllegalStateException("The clocks of the vessel and the MOC are not " +
        //              "synchronized.");
        //  }

        State.getInstance().updateLatency(vesselId, latency);
        final LatencyMessage reply = new LatencyMessage().withSent(sent).withReceived(now);
        return new WebSocketReply(vrgpMessage -> vrgpMessage.withTime(reply));
    }

    @Override
    public Function<VrgpMessage, LatencyMessage> getSingleMessage() {
        return VrgpMessage::getTime;
    }
}

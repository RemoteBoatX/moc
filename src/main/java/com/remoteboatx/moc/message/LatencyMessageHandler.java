package com.remoteboatx.moc.message;

import com.remoteboatx.moc.websocket.FrontendWebSocketMessageHandler;
import org.json.simple.JSONObject;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Calendar;

/**
 * Message handler for VRGP latency messages.
 */
public class LatencyMessageHandler implements VrgpMessageHandler {

    @Override
    public void handleMessage(WebSocketSession vesselSession, Object message,
                              FrontendWebSocketMessageHandler frontendMessageHandler) {

        long now = Calendar.getInstance().getTimeInMillis();

        // TODO: Handle ClassCastException.
        JSONObject json = (JSONObject) message;

        if (json.containsKey("received") && json.containsKey("sent")) {
            long sent, received;
            try {
                sent = (long) json.get("sent");
                received = (long) json.get("received");
            } catch (ClassCastException e) {
                throw new IllegalArgumentException("\"sent\" and \"received\" have to be " +
                        "numerical values in the \"time\" message.");
            }

            long latencyOutgoing = received - sent;
            long latencyIncoming = now - received;
            long latencyRoundTrip = now - sent;

            // TODO: This check does not guarantee clock synchronisation. This should be ensured
            //  elsewhere.
            //  if (latencyOutgoing < 0 || latencyIncoming < 0) {
            //      throw new IllegalStateException("The clocks of the vessel and the MOC are not " +
            //              "synchronized.");
            //  }

            // TODO: Format messages to frontend properly.
            frontendMessageHandler.sendMessage(String.format("outgoing: %d, incoming: %d, round " +
                    "trip: %d", latencyOutgoing, latencyIncoming, latencyRoundTrip));
        } else if (json.containsKey("sent")) {
            long sent;
            try {
                sent = (long) json.get("sent");
            } catch (ClassCastException e) {
                throw new IllegalArgumentException("\"sent\" has to be a numerical value in the " +
                        "\"time\" message.");
            }

            long latencyIncoming = now - sent;

            // TODO: This check does not guarantee clock synchronisation. This should be ensured
            //  elsewhere.
            if (latencyIncoming < 0) {
                throw new IllegalStateException("The clocks of the vessel and the MOC are not " +
                        "synchronized.");
            }

            // TODO: Format messages to frontend properly.
            frontendMessageHandler.sendMessage(String.format("incoming: %d", latencyIncoming));

            // TODO: Create Java class to model time message.
            try {
                vesselSession.sendMessage(new TextMessage(
                        String.format("{\"time\": {\"sent\": %d, \"received\": %d}}", sent, now)
                ));
            } catch (IOException e) {
                // TODO: Handle IOException.
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("\"time\" message was not formatted correctly.");
        }
    }
}

package com.remoteboatx.moc.message;

import com.remoteboatx.moc.websocket.FrontendWebSocketMessageHandler;
import org.json.simple.JSONObject;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Calendar;

public class LatencyMessageHandler implements VrgpMessageHandler {

    @Override
    public void handleMessage(WebSocketSession vesselSession, Object message,
                              FrontendWebSocketMessageHandler frontendMessageHandler) {

        long now = Calendar.getInstance().getTimeInMillis();

        // TODO: Handle ClassCastException.
        JSONObject json = (JSONObject) message;

        if (json.containsKey("received") && json.containsKey("sent")) {
            // Calculate outgoing, incoming and round trip latency.

            // TODO: Handle ClassCastException.
            long sent = (long) json.get("sent");
            long received = (long) json.get("received");

            // TODO: Check plausibility. latencyOutgoing or latencyIncoming might be negative due
            //  to lack of clock sync.
            long latencyOutgoing = received - sent;
            long latencyIncoming = now - received;
            long latencyRoundTrip = now - sent;

            // TODO: Java object to JSON.
            frontendMessageHandler.sendMessage(String.format("outgoing: %d, incoming: %d, round " +
                    "trip: %d", latencyOutgoing, latencyIncoming, latencyRoundTrip));
        } else if (json.containsKey("sent")) {
            // Calculate incoming latency and send back time message.

            // TODO: Handle ClassCastException.
            long sent = (long) json.get("sent");

            long latencyIncoming = now - sent;

            // TODO: Java object to JSON.
            frontendMessageHandler.sendMessage(String.format("incoming: %d", latencyIncoming));

            // TODO: Java object to JSON.
            try {
                vesselSession.sendMessage(new TextMessage(
                        String.format("{\"time\": {\"sent\": %d, \"received\": %d}}", sent, now)
                ));
            } catch (IOException e) {
                // TODO: Handle IOException.
                e.printStackTrace();
            }
        } else {
            // TODO: Handle incorrect message format.
        }
    }
}

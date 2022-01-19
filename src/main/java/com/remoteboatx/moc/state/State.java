package com.remoteboatx.moc.state;

import com.remoteboatx.moc.websocket.handler.FrontendMessageHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Holds the current state of the MOC mainly consisting of the information about the connected
 * vessels.
 */
public class State {

    private static State instance;

    private final Map<String, Vessel> vessels = new HashMap<>();

    private FrontendMessageHandler frontendMessageHandler;

    private State() {
    }

    public static State getInstance() {
        if (instance == null) {
            instance = new State();
        }
        return instance;
    }

    /**
     * Sets the FrontendMessageHandler to display changes in the state in the connected frontends.
     */
    public void setFrontendMessageHandler(FrontendMessageHandler frontendMessageHandler) {
        this.frontendMessageHandler = frontendMessageHandler;
    }

    public void addVessel(String vesselId) {
        vessels.put(vesselId, new Vessel());

        // TODO: Format messages to frontend properly.
        frontendMessageHandler.sendMessage(String.format("{\"%s\": {\"connected\": true}}",
                vesselId));
    }

    public void removeVessel(String vesselId) {
        vessels.remove(vesselId);

        // TODO: Format messages to frontend properly.
        frontendMessageHandler.sendMessage(String.format("{\"%s\": {\"connected\": false}}",
                vesselId));
    }

    public void updateLatency(String vesselId, Latency latency) {
        vessels.get(vesselId).setLatency(latency);

        // TODO: Format messages to frontend properly using latency class.
        final String latencyMessage = String.format("outgoing: %d, incoming: %d, round trip: %d",
                latency.getOutgoing(), latency.getIncoming(), latency.getRoundTrip());
        sendMessageToFrontends(vesselId, String.format("{\"latency\": \"%s\"}", latencyMessage));
    }

    public void updateAvailableStreams(String vesselId, List<String> availableStreams) {
        vessels.get(vesselId).setAvailableStreams(availableStreams);

        // TODO: Format messages to frontend properly using latency class.
        final StringBuilder availableStreamsMessage = new StringBuilder("[");
        for (String stream : availableStreams) {
            availableStreamsMessage.append("\"");
            availableStreamsMessage.append(stream);
            availableStreamsMessage.append("\",");
        }
        if (!availableStreams.isEmpty()) {
            availableStreamsMessage.deleteCharAt(availableStreamsMessage.length() - 1);
        }
        availableStreamsMessage.append("]");
        sendMessageToFrontends(vesselId, String.format("{\"streams\": %s}",
                availableStreamsMessage));
    }

    private void sendMessageToFrontends(String vesselId, String message) {
        frontendMessageHandler.sendMessage(String.format("{\"%s\": %s}", vesselId, message));
    }
}

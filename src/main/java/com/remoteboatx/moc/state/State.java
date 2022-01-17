package com.remoteboatx.moc.state;

import com.remoteboatx.moc.websocket.handler.FrontendMessageHandler;

import java.util.HashMap;
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
        String latencyMessage = String.format("outgoing: %d, incoming: %d, round trip: %d",
                latency.getOutgoing(), latency.getIncoming(), latency.getRoundTrip());
        frontendMessageHandler.sendMessage(String.format("{\"%s\": {\"latency\": \"%s\"}}",
                vesselId, latencyMessage));
    }
}

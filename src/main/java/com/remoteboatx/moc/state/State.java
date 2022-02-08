package com.remoteboatx.moc.state;

import com.remoteboatx.moc.frontend.message.OutgoingFrontendMessage;
import com.remoteboatx.moc.frontend.message.VesselUpdate;
import com.remoteboatx.moc.vrgp.message.VesselInformation;
import com.remoteboatx.moc.vrgp.message.stream.Conning;
import com.remoteboatx.moc.websocket.handler.FrontendWebSocketMessageHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds the current state of the MOC mainly consisting of the information about the connected vessels.
 */
public class State {

    private static State instance;

    private final Map<String, Vessel> vessels = new HashMap<>();

    private FrontendWebSocketMessageHandler frontendMessageHandler;

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
    public void setFrontendMessageHandler(FrontendWebSocketMessageHandler frontendMessageHandler) {
        this.frontendMessageHandler = frontendMessageHandler;
    }

    public OutgoingFrontendMessage getAsFrontendMessage() {
        final OutgoingFrontendMessage message = new OutgoingFrontendMessage();
        for (String vesselId : vessels.keySet()) {
            message.withVesselUpdate(vesselId, vessels.get(vesselId).getAsVesselUpdate());
        }
        message.asUpdate(false);
        return message;
    }

    /**
     * Adds a newly connected vessel to the current State.
     */
    public void addVessel(String vesselId) {
        vessels.put(vesselId, new Vessel());

        frontendMessageHandler.sendMessage(
                new OutgoingFrontendMessage().withVesselUpdate(vesselId, new VesselUpdate().withConnected(true))
                        .toJson());
    }

    /**
     * Removes a disconnected vessel from the current State.
     */
    public void removeVessel(String vesselId) {
        vessels.remove(vesselId);

        frontendMessageHandler.sendMessage(
                new OutgoingFrontendMessage().withVesselUpdate(vesselId, new VesselUpdate().withConnected(false))
                        .toJson());
    }

    /**
     * Updates the {@link Latency} information of a vessel in the current State.
     */
    public void updateLatency(String vesselId, Latency latency) {
        // TODO: What to do when only incoming latency is updated?
        vessels.get(vesselId).setLatency(latency);

        frontendMessageHandler.sendMessage(
                new OutgoingFrontendMessage().withVesselUpdate(vesselId, new VesselUpdate().withLatency(latency))
                        .toJson());
    }

    /**
     * Updates the currently available information streams of a vessel.
     */
    public void updateVesselInformation(String vesselId, VesselInformation vesselInformation) {
        vessels.get(vesselId).setVesselInformation(vesselInformation);

        frontendMessageHandler.sendMessage(new OutgoingFrontendMessage().withVesselUpdate(vesselId,
                new VesselUpdate().withVesselInformation(vesselInformation)).toJson());
    }

    /**
     * Updates the {@link Conning} information of a vessel.
     */
    public void updateConning(String vesselId, Conning conning) {
        vessels.get(vesselId).setConning(conning);

        frontendMessageHandler.sendMessage(
                new OutgoingFrontendMessage().withVesselUpdate(vesselId, new VesselUpdate().withConning(conning))
                        .toJson());
    }
}

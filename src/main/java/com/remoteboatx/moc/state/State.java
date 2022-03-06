package com.remoteboatx.moc.state;

import com.remoteboatx.moc.frontend.message.OutgoingFrontendMessage;
import com.remoteboatx.moc.frontend.message.VesselUpdate;
import com.remoteboatx.moc.vrgp.message.Status;
import com.remoteboatx.moc.vrgp.message.Streams;
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
    public synchronized void setFrontendMessageHandler(FrontendWebSocketMessageHandler frontendMessageHandler) {
        this.frontendMessageHandler = frontendMessageHandler;
    }

    public synchronized OutgoingFrontendMessage getAsFrontendMessage() {
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
    public synchronized void addVessel(String vesselId) {
        vessels.put(vesselId, new Vessel());

        frontendMessageHandler.sendMessage(
                new OutgoingFrontendMessage().withVesselUpdate(vesselId, new VesselUpdate().withConnected(true))
                        .toJson());
    }

    /**
     * Removes a disconnected vessel from the current State.
     */
    public synchronized void removeVessel(String vesselId) {
        vessels.remove(vesselId);

        frontendMessageHandler.sendMessage(
                new OutgoingFrontendMessage().withVesselUpdate(vesselId, new VesselUpdate().withConnected(false))
                        .toJson());
    }

    /**
     * Updates the {@link Latency} information of a vessel in the current State.
     */
    public synchronized void updateLatency(String vesselId, Latency latency) {
        // TODO: What to do when only incoming latency is updated?
        vessels.get(vesselId).setLatency(latency);

        frontendMessageHandler.sendMessage(
                new OutgoingFrontendMessage().withVesselUpdate(vesselId, new VesselUpdate().withLatency(latency))
                        .toJson());
    }

    /**
     * Updates the currently available information streams of a vessel.
     */
    public synchronized void updateVesselInformation(String vesselId, VesselInformation vesselInformation) {
        vessels.get(vesselId).setVesselInformation(vesselInformation);

        frontendMessageHandler.sendMessage(new OutgoingFrontendMessage().withVesselUpdate(vesselId,
                new VesselUpdate().withVesselInformation(vesselInformation)).toJson());
    }

    public synchronized void updateStreams(String vesselId, Streams streams) {
        vessels.get(vesselId).setStreams(streams);

        frontendMessageHandler.sendMessage(
                new OutgoingFrontendMessage().withVesselUpdate(vesselId, new VesselUpdate().withStreams(streams))
                        .toJson());
    }

    /**
     * Updates the {@link Conning} information of a vessel.
     */
    public synchronized void updateConning(String vesselId, Conning conning) {
        vessels.get(vesselId).setConning(conning);

        frontendMessageHandler.sendMessage(
                new OutgoingFrontendMessage().withVesselUpdate(vesselId, new VesselUpdate().withConning(conning))
                        .toJson());
    }

    public synchronized void updateStatus(String vesselId, Status status) {
        final Vessel vessel = vessels.get(vesselId);
        final Status storedStatus = vessel.getStatus(status.getId());

        if (storedStatus != null) {
            // If status has been raised before, the vessel can only cancel it.
            storedStatus.withCancelled(status.getCancelled());
        } else {
            vessel.addStatus(status);
        }

        frontendMessageHandler.sendMessage(new OutgoingFrontendMessage().withVesselUpdate(vesselId,
                new VesselUpdate().withStatuses(vessel.getStatuses())).toJson());
    }
}

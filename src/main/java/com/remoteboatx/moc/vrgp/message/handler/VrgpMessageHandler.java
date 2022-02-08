package com.remoteboatx.moc.vrgp.message.handler;

import com.remoteboatx.moc.vrgp.message.VrgpMessage;
import com.remoteboatx.moc.vrgp.message.status.StatusMessage;
import com.remoteboatx.moc.websocket.WebSocketAction;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// TODO: Maybe implement VrgpSingleMessageHandler and make List<WebSocketAction> implement WebSocketAction.

/**
 * Message handler for {@link VrgpMessage}s which contains specific message handlers for the included VRGP messages.
 */
public class VrgpMessageHandler {

    private final VesselInformationMessageHandler vesselMessageHandler = new VesselInformationMessageHandler();

    private final ConningMessageHandler conningMessageHandler = new ConningMessageHandler();

    private final LatencyMessageHandler latencyMessageHandler = new LatencyMessageHandler();

    private final StatusMessageHandler statusMessageHandler = new StatusMessageHandler();

    private final ByeMessageHandler byeMessageHandler = new ByeMessageHandler();

    /**
     * Handles a {@link VrgpMessage}.
     *
     * @param vesselId the internal ID of the vessel.
     * @param message  the message content.
     * @return a list of WebSocketActions to be executed.
     */
    @NonNull
    public List<WebSocketAction> handleMessage(String vesselId, @NonNull VrgpMessage message) {
        final List<WebSocketAction> result = new ArrayList<>();

        if (message.getVessel() != null) {
            result.add(vesselMessageHandler.handleMessage(vesselId, message.getVessel()));
        }
        if (message.getTime() != null) {
            result.add(latencyMessageHandler.handleMessage(vesselId, message.getTime()));
        }
        if (message.getBye() != null) {
            result.add(byeMessageHandler.handleMessage(vesselId, message.getBye()));
        }
        if (message.getConning() != null) {
            result.add(conningMessageHandler.handleMessage(vesselId, message.getConning()));
        }
        final Map<StatusMessage.Type, StatusMessage> statusMessages = message.getStatusMessages();
        if (!statusMessages.isEmpty()) {
            // TODO: Pass type to handler.
            statusMessages.forEach(
                    (type, statusMessage) -> statusMessageHandler.handleMessage(vesselId, statusMessage));
        }

        return result;
    }
}

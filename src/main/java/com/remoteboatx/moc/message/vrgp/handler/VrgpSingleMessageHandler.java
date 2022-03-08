package com.remoteboatx.moc.message.vrgp.handler;

import com.remoteboatx.moc.message.vrgp.VrgpMessage;
import com.remoteboatx.moc.message.vrgp.VrgpSingleMessage;
import com.remoteboatx.moc.websocket.WebSocketAction;
import org.springframework.lang.NonNull;

import java.util.function.Function;

/**
 * Interface for VRGP message handlers that handle messages of a specific type.
 *
 * @param <T> the specific VRGP message type this handler handles.
 */
public interface VrgpSingleMessageHandler<T extends VrgpSingleMessage> {

    /**
     * Handles a VRGP message of a specific type.
     *
     * @param vesselId the internal ID of the vessel.
     * @param message  the message content.
     * @return a WebSocketAction to be executed.
     */
    @NonNull
    WebSocketAction handleMessage(String vesselId, @NonNull T message);

    /**
     * Handles a specific message key of a VRGP message.
     *
     * @param vesselId    the internal ID of the vessel.
     * @param vrgpMessage the message.
     * @return a WebSocketAction to be executed.
     */
    @NonNull
    default WebSocketAction handleMessage(String vesselId, VrgpMessage vrgpMessage) {
        final T singleMessage = getSingleMessage().apply(vrgpMessage);
        if (singleMessage == null) {
            return WebSocketAction.NONE;
        }
        return handleMessage(vesselId, singleMessage);
    }

    /**
     * Returns a function that extracts the single VRGP message from a {@link VrgpMessage}.
     * <br><br>
     * <b>Caution</b>: This is supposed to simply return a getter of VrgpMessage. The actual VrgpMessage that can be
     * accessed in the returned function should not be used for anything else.
     */
    Function<VrgpMessage, T> getSingleMessage();
}

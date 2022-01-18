package com.remoteboatx.moc.message.handler;

import com.remoteboatx.moc.message.VrgpSingleMessage;
import com.remoteboatx.moc.websocket.WebSocketAction;
import org.springframework.lang.NonNull;

/**
 * Interface for VRGP message handlers that handle messages of a specific type.
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
}

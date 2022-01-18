package com.remoteboatx.moc.message.handler;

import com.remoteboatx.moc.websocket.WebSocketAction;
import org.springframework.lang.NonNull;

/**
 * Interface for VRGP message handlers that handle messages of a specific type.
 */
public interface VrgpMessageHandler {

    /**
     * Handles a VRGP message of a specific type.
     *
     * @param vesselId the internal ID of the vessel.
     * @param message  the message content.
     * @return a WebSocketAction to be executed.
     */
    @NonNull
    WebSocketAction handleMessage(String vesselId, String message);
}

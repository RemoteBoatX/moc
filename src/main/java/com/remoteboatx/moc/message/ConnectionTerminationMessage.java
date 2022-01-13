package com.remoteboatx.moc.message;

import com.fasterxml.jackson.databind.JsonNode;

public class ConnectionTerminationMessage extends AbstractVrgpMessage {

    @Override
    protected VrgpMessageType getMessageType() {
        return VrgpMessageType.TERMINATE_CONNECTION;
    }

    @Override
    public JsonNode toJson() {
        return getObjectMapper().createObjectNode().set(getMessageType().getMessageKey(), null);
    }
}

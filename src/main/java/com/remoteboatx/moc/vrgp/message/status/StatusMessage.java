package com.remoteboatx.moc.vrgp.message.status;

import com.remoteboatx.moc.vrgp.message.VrgpSingleMessage;

public class StatusMessage implements VrgpSingleMessage {

    private String category;

    private String id;

    private Long raised;

    private Long cancelled;

    private Long acknowledged;

    // TODO: Additional properties.
}

package com.remoteboatx.moc.state;

import com.remoteboatx.moc.vrgp.message.status.StatusMessage;

public class Status {

    private StatusMessage.Type type;

    private String category;

    private String id;

    private Long raised;

    private Long cancelled;

    private Long acknowledged;
}

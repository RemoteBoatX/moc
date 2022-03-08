package com.remoteboatx.moc.message.vrgp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AuthenticateMessage implements VrgpSingleMessage {

    @JsonProperty(required = true)
    private List<String> type;

    @JsonProperty(required = true)
    private String nonce;

    /**
     * Returns a list of authentication method names that can be used by the recipient of the message.
     */
    public List<String> getType() {
        return type;
    }

    /**
     * Returns a nonce the authenticating party is supposed to sign.
     */
    public String getNonce() {
        return nonce;
    }
}

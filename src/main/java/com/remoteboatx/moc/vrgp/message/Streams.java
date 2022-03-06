package com.remoteboatx.moc.vrgp.message;

/**
 * JSON model of the VRGP streams message that includes the available information streams and additional information
 * about the streams.
 */
public class Streams implements VrgpSingleMessage {

    // TODO: Refactor to actual datatypes.
    private boolean conning;

    private boolean camera;

    public boolean getConning() {
        return conning;
    }

    public boolean getCamera() {
        return camera;
    }
}

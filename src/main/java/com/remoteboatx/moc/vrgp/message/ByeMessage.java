package com.remoteboatx.moc.vrgp.message;

/**
 * JSON model of the VRGP bye message that indicates an intentional closure of the VRGP connection.
 */
public class ByeMessage implements VrgpSingleMessage {

    // TODO: Ask Robert what the content of the bye message is.

    private boolean over = true;

    public boolean isOver() {
        return over;
    }
}

package com.remoteboatx.moc.vrgp.message.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.remoteboatx.moc.vrgp.message.VrgpSingleMessage;

/**
 * JSON model for the VRGP conning message.
 */
public class Conning implements VrgpSingleMessage {

    @JsonProperty(required = true)
    private long lat;

    @JsonProperty(value = "long", required = true)
    private long longitude;

    @JsonProperty(required = true)
    private float heading;

    @JsonProperty(required = true)
    private float cog;

    @JsonProperty(required = true)
    private float sog;

    // TODO: Model position.
    private JsonNode position;

    private float rot;

    // TODO: Model steering.
    private JsonNode steering;

    // TODO: Model propulsion.
    private JsonNode propulsion;

    private float stw;

    private float aws;

    private float awa;

    private float depth;

    /**
     * Returns the latitude.
     */
    public long getLat() {
        return lat;
    }

    /**
     * Returns the longitude.
     */
    public long getLong() {
        return longitude;
    }

    /**
     * Returns the true heading of the vessel in degrees.
     */
    public float getHeading() {
        return heading;
    }

    /**
     * Returns the course over ground.
     */
    public float getCog() {
        return cog;
    }

    /**
     * Returns the speed over ground in knots.
     */
    public float getSog() {
        return sog;
    }

    /**
     * Returns additional position metadata.
     */
    public JsonNode getPosition() {
        return position;
    }

    /**
     * Returns the rotation in degrees per minute.
     */
    public float getRot() {
        return rot;
    }

    /**
     * Returns the steering of the vessel.
     */
    public JsonNode getSteering() {
        return steering;
    }

    /**
     * Returns the propulsion of the vessel.
     */
    public JsonNode getPropulsion() {
        return propulsion;
    }

    /**
     * Returns the forward (longitudinal) speed in knots. Negative numbers for reverse motion.
     */
    public float getStw() {
        return stw;
    }

    /**
     * Returns the apparent (relative) wind speed in knots.
     */
    public float getAws() {
        return aws;
    }

    /**
     * Returns the apparent (relative) wind angle from bow.
     */
    public float getAwa() {
        return awa;
    }

    /**
     * Returns the depth under keel.
     */
    public float getDepth() {
        return depth;
    }
}

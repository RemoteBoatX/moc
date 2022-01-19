package com.remoteboatx.moc.vrgp.message.stream;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.remoteboatx.moc.vrgp.message.VrgpSingleMessage;

public class Conning implements VrgpSingleMessage {

    // Latitude.
    @JsonProperty(required = true)
    private long lat;

    @JsonProperty(value = "long", required = true)
    private long longitude;

    // True heading of the ship in degrees.
    @JsonProperty(required = true)
    private float heading;

    // Course over ground.
    @JsonProperty(required = true)
    private float cog;

    // Speed over ground in knots.
    @JsonProperty(required = true)
    private float sog;

    // TODO: Model position.
    private JsonNode position;

    // Rotation in degrees per minute.
    private float rot;

    // TODO: Model steering.
    private JsonNode steering;

    // TODO: Model propulsion.
    private JsonNode propulsion;

    // Forward (longitudinal) speed in knots. Negative numbers for reverse motion.
    private float stw;

    // Apparent (relative) wind speed in knots.
    private float aws;

    // Apparent (relative) wind angle from bow.
    private float awa;

    // Depth under keel.
    private float depth;

    public long getLat() {
        return lat;
    }

    public long getLong() {
        return longitude;
    }

    public float getHeading() {
        return heading;
    }

    public float getCog() {
        return cog;
    }

    public float getSog() {
        return sog;
    }

    public JsonNode getPosition() {
        return position;
    }

    public float getRot() {
        return rot;
    }

    public JsonNode getSteering() {
        return steering;
    }

    public JsonNode getPropulsion() {
        return propulsion;
    }

    public float getStw() {
        return stw;
    }

    public float getAws() {
        return aws;
    }

    public float getAwa() {
        return awa;
    }

    public float getDepth() {
        return depth;
    }
}

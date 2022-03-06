package com.remoteboatx.moc.vrgp.message;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * JSON model of the VRGP vessel message that includes mostly static information about the vessel.
 */
public class VesselInformation implements VrgpSingleMessage {

    @JsonProperty(required = true)
    private String mmsi;

    private String call;

    private String name;

    @JsonProperty(required = true)
    private Float loa;

    @JsonProperty(required = true)
    private Float breadth;

    @JsonProperty(required = true)
    private Float height;

    // TODO: Draft can also be an objet with "foreward", "aft" and "summer" properties.
    @JsonProperty(required = true)
    private Float draft;

    @JsonProperty("from_above")
    private String fromAbove;

    @JsonProperty("from_abaft")
    private String fromAbaft;

    private Boolean simulation;

    // TODO: Remove and implement as separate message.
    @JsonProperty(required = true)
    private Streams streams;

    /**
     * Returns the Maritime Mobile Service Identifier (MMSI) string of the vessel.
     */
    public String getMmsi() {
        return mmsi;
    }

    /**
     * Returns the international radio call sign assigned to the vessel.
     */
    public String getCall() {
        return call;
    }

    /**
     * Returns the name of the vessel as visible on the vessel.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the overall length of the vessel, in meters, rounded to one decimal.
     */
    public Float getLoa() {
        return loa;
    }

    /**
     * Returns the maximum overall width of the vessel, in meters, rounded to one decimal.
     */
    public Float getBreadth() {
        return breadth;
    }

    /**
     * Returns the overall height of the vessel, in meters, rounded to one decimal.
     */
    public Float getHeight() {
        return height;
    }

    /**
     * Returns the average draft.
     */
    public Float getDraft() {
        return draft;
    }

    /**
     * Returns the drawing commands of an SVG path element, such that the outline of the defined path corresponds with
     * the outline of the vessel as seen from above.
     */
    @JsonProperty("from_above")
    public String getFromAbove() {
        return fromAbove;
    }

    /**
     * Returns the drawing commands of an SVG path element, such that the outline of the defined path corresponds with
     * the outline of the vessel as seen from abaft (behind).
     */
    @JsonProperty("from_abaft")
    public String getFromAbaft() {
        return fromAbaft;
    }

    /**
     * Returns {@code true} if the vessel is a (computer) simulation, and {@code false} otherwise.
     */
    public Boolean getSimulation() {
        return simulation;
    }

    /**
     * Returns the available streams of the vessel defined in the vessel message.
     */
    public Streams getStreams() {
        return streams;
    }
}

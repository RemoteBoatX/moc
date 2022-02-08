package com.remoteboatx.moc.frontend.message;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.remoteboatx.moc.state.Latency;
import com.remoteboatx.moc.vrgp.message.VesselInformationMessage;
import com.remoteboatx.moc.vrgp.message.stream.Conning;

public class VesselUpdate {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean connected;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Latency latency;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private VesselInformationMessage vessel;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Conning conning;
}

package com.remoteboatx.moc.message;

import com.remoteboatx.moc.FrontendMessageHandler;
import org.json.simple.JSONObject;

public class VesselInfoMessageHandler {

    private VesselInfoMessageHandler() {
    }

    public static void handleMessage(JSONObject json,
                                     FrontendMessageHandler frontendMessageHandler) {

        frontendMessageHandler.sendMessage(json);
    }
}

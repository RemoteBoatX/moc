package com.remoteboatx.moc.message;

import com.remoteboatx.moc.websocket.FrontendWebSocketMessageHandler;
import org.springframework.web.socket.WebSocketSession;

public class VesselInfoMessageHandler implements VrgpMessageHandler {

    @Override
    public void handleMessage(WebSocketSession vesselSession, Object message,
                              FrontendWebSocketMessageHandler frontendMessageHandler) {
        frontendMessageHandler.sendMessage(message.toString());
    }
}

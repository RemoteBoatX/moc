package com.remoteboatx.moc.message;

import com.remoteboatx.moc.websocket.FrontendWebSocketMessageHandler;
import org.springframework.web.socket.WebSocketSession;

public interface VrgpMessageHandler {

    void handleMessage(WebSocketSession vesselSession, Object message,
                       FrontendWebSocketMessageHandler frontendMessageHandler);
}

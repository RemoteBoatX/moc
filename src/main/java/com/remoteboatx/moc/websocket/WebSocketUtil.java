package com.remoteboatx.moc.websocket;

import org.springframework.web.socket.WebSocketSession;

public class WebSocketUtil {

    public static final String MMSI_KEY = "mmsi";

    private WebSocketUtil() {
    }

    public static String extractMmsiFromWebSocketSession(WebSocketSession session) {
        return (String) session.getAttributes().get(MMSI_KEY);
    }
}

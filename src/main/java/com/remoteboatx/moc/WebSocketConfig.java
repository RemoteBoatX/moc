package com.remoteboatx.moc;

import com.remoteboatx.moc.websocket.WebSocketUtil;
import com.remoteboatx.moc.websocket.handler.WebSocketMessageHandler;
import com.remoteboatx.moc.websocket.handler.WebSocketMessageHandlerWrapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private static final String VESSEL_PATH = "/vessel/";

    // TODO: Add MOC state holding static and dynamic vessel information.

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        // Use the same message handler for both vessels and frontends.
        final WebSocketMessageHandler messageHandler = new WebSocketMessageHandler();

        webSocketHandlerRegistry.addHandler(
                        new WebSocketMessageHandlerWrapper(messageHandler, WebSocketMessageHandler.ConnectionType.VESSEL),
                        VESSEL_PATH + "{mmsi}")
                // TODO: Set to actually allowed origins.
                .setAllowedOriginPatterns("*")
                // Retrieve MMSI url param to make it available in the WebSocketMessageHandler.
                .addInterceptors(new MmsiUrlParamHandshakeInterceptor());

        webSocketHandlerRegistry.addHandler(
                        new WebSocketMessageHandlerWrapper(messageHandler, WebSocketMessageHandler.ConnectionType.FRONTEND),
                        "/frontend")
                // TODO: Set to actually allowed origins.
                .setAllowedOriginPatterns("*");
    }

    private static class MmsiUrlParamHandshakeInterceptor implements HandshakeInterceptor {

        @Override
        public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                       WebSocketHandler wsHandler, Map<String, Object> attributes) {

            final String path = request.getURI().getPath();
            final String mmsi = path.substring(path.indexOf(VESSEL_PATH) + VESSEL_PATH.length());
            attributes.put(WebSocketUtil.MMSI_KEY, mmsi);
            return true;
        }

        @Override
        public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Exception exception) {
        }
    }
}

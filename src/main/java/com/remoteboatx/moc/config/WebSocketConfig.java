package com.remoteboatx.moc.config;

import com.remoteboatx.moc.websocket.handler.WebSocketMessageHandler;
import com.remoteboatx.moc.websocket.handler.WebSocketMessageHandlerWrapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    // TODO: Add MOC state holding static and dynamic vessel information.

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {

        // Use the same message handler for both vessels and frontends.
        final WebSocketMessageHandler messageHandler = new WebSocketMessageHandler();

        webSocketHandlerRegistry.addHandler(
                        new WebSocketMessageHandlerWrapper(messageHandler,
                                WebSocketMessageHandler.ConnectionType.VESSEL), "/vessel")
                // TODO: Set to actually allowed origins.
                .setAllowedOriginPatterns("*");

        webSocketHandlerRegistry.addHandler(
                        new WebSocketMessageHandlerWrapper(messageHandler,
                                WebSocketMessageHandler.ConnectionType.FRONTEND), "/frontend")
                // TODO: Set to actually allowed origins.
                .setAllowedOriginPatterns("*");
    }
}

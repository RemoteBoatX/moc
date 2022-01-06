package com.remoteboatx.moc;

import com.remoteboatx.moc.websocket.WebSocketMessageHandler;
import com.remoteboatx.moc.websocket.WebSocketMessageHandlerWrapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

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

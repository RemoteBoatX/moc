package com.remoteboatx.moc;

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
        final WebSocketMessageHandler webSocketMessageHandler = new WebSocketMessageHandler();

        webSocketHandlerRegistry.addHandler(
                        new WebSocketHandler() {
                            @Override
                            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                                webSocketMessageHandler.afterConnectionEstablished(session, WebSocketMessageHandler.ConnectionType.VESSEL);
                            }

                            @Override
                            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
                                webSocketMessageHandler.handleMessage(session, message);
                            }

                            @Override
                            public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
                                webSocketMessageHandler.handleTransportError(session, exception);
                            }

                            @Override
                            public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
                                webSocketMessageHandler.afterConnectionClosed(session, closeStatus);
                            }

                            @Override
                            public boolean supportsPartialMessages() {
                                return webSocketMessageHandler.supportsPartialMessages();
                            }
                        },
                        "/vessel")
                // TODO: Set to actually allowed origins.
                .setAllowedOriginPatterns("*");

        webSocketHandlerRegistry.addHandler(
                        new WebSocketHandler() {
                            @Override
                            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                                webSocketMessageHandler.afterConnectionEstablished(session, WebSocketMessageHandler.ConnectionType.FRONTEND);
                            }

                            @Override
                            public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
                                webSocketMessageHandler.handleMessage(session, message);
                            }

                            @Override
                            public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
                                webSocketMessageHandler.handleTransportError(session, exception);
                            }

                            @Override
                            public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
                                webSocketMessageHandler.afterConnectionClosed(session, closeStatus);
                            }

                            @Override
                            public boolean supportsPartialMessages() {
                                return webSocketMessageHandler.supportsPartialMessages();
                            }
                        }, "/frontend")
                // TODO: Set to actually allowed origins.
                .setAllowedOriginPatterns("*");
    }
}

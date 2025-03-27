package com.gn.mvc.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSocket // 웹소켓에 대한 환경 설정
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketConfigurer{
	
	private final BasicWebSocketHandler basicWebSocketHandler;
	private final ChatWebSocketHandler chatWebSocketHandler;
	
	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
		registry.addHandler(basicWebSocketHandler, "/ws/basic")
				.setAllowedOrigins("http://localhost:8080"); // 통신을 하고 있는 도메인
		
		registry.addHandler(chatWebSocketHandler, "/ws/chat")
				.setAllowedOrigins("http://localhost:8080");
	}
	

}

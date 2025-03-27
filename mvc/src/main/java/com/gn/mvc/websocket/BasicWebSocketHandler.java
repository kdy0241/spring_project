package com.gn.mvc.websocket;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import lombok.NoArgsConstructor;

// 백엔드 부분(WebSocket)
@Component // Bean으로 등록하고 싶은데 기본으로 쓰는 어노테이션
@NoArgsConstructor // 매개변수없는 기본 생성자
public class BasicWebSocketHandler extends TextWebSocketHandler{
	
	private static final List<WebSocketSession> sessionList = new ArrayList<WebSocketSession>();

	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		// 새로운 WebSocket 연결(open)된 순간
		//System.out.println("서버 : 연결");
		sessionList.add(session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		// 클라이언트 -> 서버 메시지 (send)한 순간
		String payload = message.getPayload();
		System.out.println("서버 : 메시지 받음 : "+payload);
		for(WebSocketSession temp : sessionList) {
			temp.sendMessage(new TextMessage(payload));
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		// WebSocket 연결 끊겼을 때
		//System.out.println("연결 끊김");
		sessionList.remove(session);
	}
	
	
}

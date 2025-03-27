package com.gn.mvc.service;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.gn.mvc.entity.ChatMsg;
import com.gn.mvc.entity.ChatRoom;
import com.gn.mvc.repository.ChatMsgRepository;
import com.gn.mvc.repository.ChatRoomRepository;
import com.gn.mvc.specification.ChatMsgSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMsgService {
	private final ChatRoomRepository chatRoomRepository;
	private final ChatMsgRepository chatMsgRepository;
	// 1. 기능 : 채팅메시지 목록 조회
	// 2. 조건 : 채팅방 번호
	// 3. 결과 : 채팅 메시지 목록
	
	
	public List<ChatMsg> selectChatMsgAll(Long id){
		
		// 조건 : 채팅방 번호
		ChatRoom chatRoom = chatRoomRepository.findById(id).orElse(null);
		// (1) 전달받은 id를 기준으로 ChatRoom 엔티티 조회
		Specification<ChatMsg> spec = (root, query, criteriaBuilder) -> null;
		spec = spec.and(ChatMsgSpecification.chatRoomEquals(chatRoom));
		// (2) ChatRoom 엔티티 기준으로 Specification 생성
		List<ChatMsg> msgList = chatMsgRepository.findAll(spec);
		// (3) spec을 매개변수로 전달하여 findAll 반환
		return msgList;
	}
	
}

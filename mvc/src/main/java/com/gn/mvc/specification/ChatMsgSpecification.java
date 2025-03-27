package com.gn.mvc.specification;

import org.springframework.data.jpa.domain.Specification;

import com.gn.mvc.entity.ChatMsg;
import com.gn.mvc.entity.ChatRoom;

public class ChatMsgSpecification {
	// 착각한게 chatMsg가 아니라 chatRoom으로 쓰는게 맞다
	public static Specification<ChatMsg> chatRoomEquals(ChatRoom chatRoom){
		return (root,query,criteriaBuilder) -> criteriaBuilder.equal(root.get("chatRoom"), chatRoom);
	}
}

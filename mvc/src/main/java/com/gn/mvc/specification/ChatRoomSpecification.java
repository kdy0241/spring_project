package com.gn.mvc.specification;

import org.springframework.data.jpa.domain.Specification;

import com.gn.mvc.entity.ChatRoom;
import com.gn.mvc.entity.Member;

public class ChatRoomSpecification {
	
	// WHERE from_member = (SELECT member_no from member WHERE 	= 회원 번호)
	
	public static Specification<ChatRoom> fromMemberEquals(Member member){
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("fromMember"), member);
	}
	// WHERE to_member = (SELECT member_no from member WHERE = )
	public static Specification<ChatRoom> toMemberEquals(Member member){
		return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("toMember"), member);
	}
}

package com.gn.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gn.mvc.entity.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long>{
	
	List<ChatRoom> findAll(Specification<ChatRoom> spec);
}

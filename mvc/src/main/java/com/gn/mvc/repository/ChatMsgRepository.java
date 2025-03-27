package com.gn.mvc.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.gn.mvc.entity.ChatMsg;

public interface ChatMsgRepository extends JpaRepository<ChatMsg, Long>{

	List<ChatMsg> findAll(Specification<ChatMsg> spec);
	
}

package com.gn.mvc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gn.mvc.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{

	Member findBymemberId(String keyword);
	
	
}

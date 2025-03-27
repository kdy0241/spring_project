package com.gn.mvc.specification;

import org.springframework.data.jpa.domain.Specification;

import com.gn.mvc.entity.Board;

public class BoardSpecification {
	// 제목에 특정 문자열이 포함된 검색 조건
	public static Specification<Board> boardTitleContains(String keyword){
		return (root, query, criteriaBuilder) -> 
			criteriaBuilder.like(root.get("boardTitle"),"%"+keyword+"%");
	}
	
	// 내용에 특정 문자열이 포함된 검색 조건
	public static Specification<Board> boardContentContains(String keyword){
		return (root, query, criteriaBuilder) ->
			criteriaBuilder.like(root.get("boardContent"),"%"+keyword+"%");
	}
}

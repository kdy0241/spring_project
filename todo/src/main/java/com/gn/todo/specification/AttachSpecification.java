package com.gn.todo.specification;

import org.springframework.data.jpa.domain.Specification;

import com.gn.todo.entity.Attach;

public class AttachSpecification {

	 public static Specification<Attach> fileNameContains(String fileName) {
	        return (root, query, criteriaBuilder) -> 
	            criteriaBuilder.like(root.get("oriName"), "%" + fileName + "%");
	    }
}

package com.gn.todo.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.gn.todo.dto.PageDto;
import com.gn.todo.dto.SearchDto;
import com.gn.todo.dto.TodoDto;
import com.gn.todo.entity.Todo;
import com.gn.todo.repository.TodoRepository;
import com.gn.todo.specification.TodoSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TodoService {
	
	private final TodoRepository todoRepository;
	
	public int deleteTodoOne(Long id) {
		int result = 0;
		try {
			Todo target = todoRepository.findById(id).orElse(null);
			if(target != null) {
				todoRepository.delete(target);
			}
			result =1;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Todo updateTodoOne(Long id) {
		Todo target = todoRepository.findById(id).orElse(null);
		
		TodoDto dto = new TodoDto().toDto(target);
		
		if(target != null) {
			if("Y".equals(target.getFlag()))  dto.setFlag("N");
			else dto.setFlag("Y");
		}

		return todoRepository.save(dto.toEntity());
	}
	
	public Todo createTodoOne(TodoDto dto) {
		Todo entity = dto.toEntity();
		Todo result = todoRepository.save(entity);
		return result;
	}
	
	public Page<Todo> selectTodoAll(SearchDto searchDto, PageDto pageDto){
		Pageable pageable
			= PageRequest.of(pageDto.getNowPage()-1, pageDto.getNumPerPage());
		
		Specification<Todo> spec = (root, query, criteriaBuilder)->null;
		if(searchDto.getSearch_text() != null) {
			spec = spec.and(TodoSpecification.todoContentContains(searchDto.getSearch_text()));
		}
		
		return todoRepository.findAll(spec,pageable);
	}
	
}
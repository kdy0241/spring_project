package com.gn.todo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.gn.todo.dto.TodoDto;
import com.gn.todo.entity.Todo;
import com.gn.todo.service.TodoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TodoController {
	
	private final TodoService todoService;
	
	@PostMapping("/todo/{id}/update")
	@ResponseBody
	public Map<String,String> updateTodoApi(@PathVariable("id") Long id){
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "500");
		resultMap.put("res_msg", "할 일 완료 여부 수정 실패");
		
		Todo result = todoService.updateTodoOne(id);
		if(result != null) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "할 일 완료 여부 수정 성공");
		}

		return resultMap;
	}
	
	@PostMapping("/todo/{id}/delete")
	@ResponseBody
	public Map<String,String> deleteTodoApi(@PathVariable("id") Long id){
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "500");
		resultMap.put("res_msg", "삭제 실패");
		
		int result = todoService.deleteTodoOne(id);
		if(result > 0) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "삭제 성공");
		}
		
		return resultMap;
	}
	

	@PostMapping("/todo/create")
	@ResponseBody
	public Map<String,String> createTodoApi(TodoDto dto){
		Map<String,String> resultMap = new HashMap<String,String>();
		resultMap.put("res_code", "500");
		resultMap.put("res_msg", "할 일 추가 실패");
		
		Todo result = todoService.createTodoOne(dto);
		if(result != null) {
			resultMap.put("res_code", "200");
			resultMap.put("res_msg", "할 일 추가 성공");			
		}
		return resultMap;
	}
}
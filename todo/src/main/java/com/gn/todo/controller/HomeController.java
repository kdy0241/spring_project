package com.gn.todo.controller;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gn.todo.dto.PageDto;
import com.gn.todo.dto.SearchDto;
import com.gn.todo.entity.Todo;
import com.gn.todo.service.TodoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
	
	private final TodoService todoService;
	
	@GetMapping({"","/"})
	public String homeView(Model model, SearchDto searchDto, PageDto pageDto) {
		
		if(pageDto.getNowPage() == 0) pageDto.setNowPage(1);

		Page<Todo> resultList = todoService.selectTodoAll(searchDto,pageDto);
		
//		if(resultList.isEmpty()) {
//			resultList = null; 
//		}
		
		pageDto.setTotalPage(resultList.getTotalPages());
		
		model.addAttribute("resultList", resultList.getContent());
		model.addAttribute("searchDto", searchDto);
		model.addAttribute("pageDto",pageDto);
		
		return "home";
	}

}
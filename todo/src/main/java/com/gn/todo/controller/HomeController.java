package com.gn.todo.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gn.todo.dto.AttachDto;
import com.gn.todo.dto.PageDto;
import com.gn.todo.dto.SearchDto;
import com.gn.todo.entity.Attach;
import com.gn.todo.entity.Todo;
import com.gn.todo.service.AttachService;
import com.gn.todo.service.TodoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
	
	private final TodoService todoService;
	private final AttachService attachService;
	
	@GetMapping({"","/"})
	public String homeView(Model model, SearchDto searchDto, PageDto pageDto) {
		
		if(pageDto.getNowPage() == 0) pageDto.setNowPage(1);

		Page<Todo> resultList = todoService.selectTodoAll(searchDto,pageDto);
		
//		if(resultList.isEmpty()) {
//			resultList = null; 
//		}
		List<Attach> attachList = attachService.selectAttachList();
		model.addAttribute("attachList", attachList);
		
		pageDto.setTotalPage(resultList.getTotalPages());
		
		model.addAttribute("resultList", resultList.getContent());
		model.addAttribute("searchDto", searchDto);
		model.addAttribute("pageDto",pageDto);
		
		return "home";
	}

}
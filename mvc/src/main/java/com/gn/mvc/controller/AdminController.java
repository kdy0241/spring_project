package com.gn.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
	// admin/read
	@GetMapping("/read")
	public String adminPage() {
		return "admin/home";
	}
}

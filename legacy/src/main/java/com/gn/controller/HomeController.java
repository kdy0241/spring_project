package com.gn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	// @GetMapping("/")
	// @RequestMapping(value="/", method=RequestMethod.GET)
	@GetMapping({"","/"})
	public String home() {
		// /WEB-INF/views/home.jsp
		return "home";
	}
	

	
	

}

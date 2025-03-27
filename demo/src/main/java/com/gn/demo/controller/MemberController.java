package com.gn.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gn.demo.vo.Member;

@Controller
public class MemberController {
	
	@GetMapping("/member")
	public String selectMemberList(Model model) {
		List<Member> resultList = new ArrayList<Member>();
		resultList.add(new Member("김철수",25));
		resultList.add(new Member("이영희",7));
		model.addAttribute("members",resultList);
		return "/member/list";
	}

}

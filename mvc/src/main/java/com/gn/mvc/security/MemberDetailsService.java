package com.gn.mvc.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gn.mvc.entity.Member;
import com.gn.mvc.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService{
	
	private final MemberRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = repository.findBymemberId(username);
		if(member == null) {
			throw new UsernameNotFoundException("존재하지 않는 회원입니다.");
		}
		return new MemberDetails(member);
	}
	
}

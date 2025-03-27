package com.gn.mvc.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.gn.mvc.entity.Member;

import lombok.RequiredArgsConstructor;

// UserDetails(스프링이 사용하는 사용자 정보 객체)를 구현한 구현체 / MemberDetails 마우스 add 추가

@RequiredArgsConstructor
public class MemberDetails implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	private final Member member;
	
	public Member getMember() {
		return member;
	}

	// 사용자 권한 설정
	// 여러 권한이 필요하다면 여러개를 추가해야함
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority(member.getMemberRole()));
		//return List.of(new SimpleGrantedAuthority("user"));
	}
	
	// 사용자 비밀번호 반환
	@Override
	public String getPassword() {
		return member.getMemberPw();
	}
	
	// 사용자 이름 반환
	@Override
	public String getUsername() {
		return member.getMemberId();
	}
	
	// 계정 상태 관리
	// is~로 시작, 반환 boolean
	
	// 계정 만료 여부 반환 메소드
	// 임시 계정, 비활성화된 계정
	@Override
	public boolean isAccountNonExpired() {
//		if(member.getExpired().equals("Y")) {
//			return false;
//		} else {
//			return true;
//		}
		return true; // 로그인이 안되게 하고싶다면 false를 쓰면 된다
	}
	
	// 계정 잠금 여부
	// 비밀번호가 5번 틀리면 -> 10분간 로그인 금지
	@Override
	public boolean isAccountNonLocked() {
//		if(member.getLockedDate() + 10 > 현재시간) {
//			return false;
//		} else {
//			return true;
//		}
		return true;
	}
	
	// 패스워드 만료 여부
	// 6개월이 지났음 -> 비밀번호 변경 요청
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	// 계정 사용 가능 여부
	@Override
	public boolean isEnabled() {
		return true;
	}
	
	
}

package com.gn.mvc.service;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.gn.mvc.dto.MemberDto;
import com.gn.mvc.entity.Member;
import com.gn.mvc.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	// member 정보를 조회하고 저장
	private final MemberRepository repository;
	// 암호화
	private final PasswordEncoder passwordEncoder;
	// 데이터베이스에 접근할때 사용하는 연결 정보
	private final DataSource dataSource;
	// Spring Security 사용자 정보를 로드하는 역할
	private final UserDetailsService userDetailsService;
	
	public MemberDto createMember(MemberDto dto) {
		// String oriPw = dto.getMember_pw();
		// String newPw = passwordEncoder.encode(oriPw);
		// dto.setMember_pw(newPw);
		
		dto.setMember_pw(passwordEncoder.encode(dto.getMember_pw()));
		Member entity = dto.toEntity();
		Member saved = repository.save(entity);
		return new MemberDto().toDto(saved);
	}
	// 1. 반환형 : Member
	// 2. 메소드명 : selectMemberOne
	// 3. 매개변수 : Long
	// 4. 역할 : PK값 기준 회원 정보 단일 조회 후 반환(return)
	public Member selectMemberOne(Long id) {
		return repository.findById(id).orElse(null);
	}
	// 1. 반환형 : int 
	// 2. 메소드명 : updateMember
	// 3. 매개변수 : MemberDto
	// 4. 역할

	public int updateMember(MemberDto param) {
		int result = 0;
		try {
			// (1) 데이터베이스 회원 정보 수정
			param.setMember_pw(passwordEncoder.encode(param.getMember_pw()));
			Member updated = repository.save(param.toEntity());
			
			if(updated != null) {
				// (2) remember-me(**DB**,cookie)가 있다면 무효화
				JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
				String sql = "DELETE FROM persistent_logins WHERE username= ?";
				jdbcTemplate.update(sql,param.getMember_id());
				// (3) 변경된 회원 정보 Security Context에 즉시 반영
				UserDetails updatedUserDetails
					= userDetailsService.loadUserByUsername(param.getMember_id());
				Authentication newAuth = new UsernamePasswordAuthenticationToken(
						updatedUserDetails,
						updatedUserDetails.getPassword(),
						updatedUserDetails.getAuthorities());
				SecurityContextHolder.getContext().setAuthentication(newAuth);
			}
			result = 1;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	// 회원 탈퇴
	public int deleteMember(Long id) {
		int result = 0;
		try {
			// 1. id를 기준으로 타겟 조회
			Member target = repository.findById(id).orElse(null);
			if(target != null) {
				repository.delete(target);
				// 2. remember-me가 있다면 무효화
				JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
				String sql = "DELETE FROM persistent_logins WHERE username = ?";
				jdbcTemplate.update(sql,target.getMemberId());
				// 3. 변경된 회원 정보 Spring Security에 즉시 반영
				SecurityContextHolder.getContext().setAuthentication(null);
			}
			result = 1;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
		

package com.gn.mvc.security;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import lombok.RequiredArgsConstructor;

@Configuration // 환경설정 파일
@EnableWebSecurity // 스프링 시큐리티 쓸꺼에요~
@RequiredArgsConstructor
public class WebSecurityConfig {
	// @RequiredArgsConstructor
	private final DataSource dataSource;
	
	// 요청중에 정적인 리소스가 있는 경우 -> Security 비활성화
	@Bean // 이걸 쓰면 public가 빠져도 돌아간다
	WebSecurityCustomizer configure() {
		return web -> web.ignoring()
				.requestMatchers(PathRequest.toStaticResources().atCommonLocations());
	}
	// 특정 요청이 들어왔을때 어떻게 처리할 것인가?
	@Bean // public가 빠져도 돌아간다
	SecurityFilterChain filterChain(HttpSecurity http, UserDetailsService customUserDetailsService) throws Exception{
		http.userDetailsService(customUserDetailsService)
				.authorizeHttpRequests(requests -> requests
				.requestMatchers("/login","/signup","/logout","/").permitAll()
			    .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자만 접근 가능
			  //  .requestMatchers("/user/**").hasAnyRole("USER","ADMIN") // 일반 사용자 + 관리자 접근 가능
				.anyRequest().authenticated())
		.formLogin(login -> login.loginPage("/login")
							.successHandler(new MyLoginSuccessHandler())
							.failureHandler(new MyLoginFailureHandler()))
		.logout(logout -> logout.logoutUrl("/logout")
							.clearAuthentication(true)
							.logoutSuccessUrl("/login")
							.invalidateHttpSession(true)
							.deleteCookies("remember-me"))
		.rememberMe(rememberMe -> rememberMe.rememberMeParameter("remember-me")
											.tokenValiditySeconds(60*60*24*30)
											.alwaysRemember(false)
											.tokenRepository(tokenRepository()));
		return http.build();
	}
	
	// 데이터베이스 접근 Bean 등록(영속성 토큰)
	// private final DataSource dataSource;
	@Bean
	PersistentTokenRepository tokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
		jdbcTokenRepository.setDataSource(dataSource);
		return jdbcTokenRepository;
	}
	
	
	// 비밀번호 암호화에 사용될 Bean 등록
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	// AuthenticationManager(인증 관리)
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	
	
	
	
	
	
}

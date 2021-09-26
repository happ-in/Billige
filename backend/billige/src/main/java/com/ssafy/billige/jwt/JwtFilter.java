package com.ssafy.billige.jwt;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.GenericFilter;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtFilter extends GenericFilter {	// 실제 인증 필터

	private final JwtProvider jwtProvider;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {

		// 1. 헤더에서 JWT 받아오기
		String token = jwtProvider.getToken((HttpServletRequest) request);

		// 2. 유효한 토큰인지 확인
		if (token != null && jwtProvider.validateToken(token)) {
			// 3. 유효한 토큰이라면 토큰에서 유저정보 받아오기
			Authentication authentication = jwtProvider.getAuthentication(token);
			// 4. SecurityContext에 Authentication 객체 저장
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		chain.doFilter(request, response);
	}
}

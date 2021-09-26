package com.ssafy.billige.jwt;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtProvider {

	private final long TOKEN_VALID_TIME = 60 * 60 * 3L;

	@Value("${jwt.secret}")
	private String secret;

	@Autowired
	private UserDetailsService userDetailsService;

	public String createToken(String email, List<String> roles) {
		Claims claims = Jwts.claims().setSubject(email);    // JWT payload 저장되는 정보단위
		claims.put("roles", roles);
		Date now = new Date();
		return Jwts.builder()
			.setClaims(claims)	// 정보저장
			.setIssuedAt(now)	// 토큰 발행 시간
			.setExpiration(new Date(now.getTime() + TOKEN_VALID_TIME))	// 토큰 유효 시간
			.signWith(SignatureAlgorithm.HS256, secret)	// 암호화 알고리즘과 서명(secret)
			.compact();
	}

	// JWT 토큰에서 인증 정보 조회
	public Authentication getAuthentication(String token) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(getUserInfo(token));
		return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
	}

	// 토큰에서 회원 정보 추출
	private String getUserInfo(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
	}

	// Request Header에서 토큰 값 추출
	public String getToken(HttpServletRequest request) {
		return request.getHeader("X-AUTH-TOKEN");
	}

	// 토큰 유효성 및 만료일자 확인
	public boolean validateToken(String token) {
		Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
		return !claims.getBody().getExpiration().before(new Date());
	}
}

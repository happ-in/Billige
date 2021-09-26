package com.ssafy.billige.user.service.impl;

import java.util.Collections;
import java.util.Map;

import com.ssafy.billige.item.domain.ActiveStatus;
import com.ssafy.billige.jwt.JwtProvider;
import com.ssafy.billige.user.domain.User;
import com.ssafy.billige.user.dto.request.UserRequest;
import com.ssafy.billige.user.repository.UserRepository;
import com.ssafy.billige.user.service.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtProvider jwtProvider;

	@Override
	public User getKakaoUser(User user) {
		return userRepository.findByTokenId(user.getTokenId()).orElseGet(() -> save(user));
	}

	@Override
	public void joinUs(UserRequest userRequest) {
		User user = User.builder()
			.username(userRequest.getUsername())
			.email(userRequest.getEmail())
			.password(passwordEncoder.encode(userRequest.getPassword()))
			.is_deleted(ActiveStatus.N)
			.address(userRequest.getAddress())
			.userSigunguCode(userRequest.getUserSigunguCode())
			.roles(Collections.singletonList("ROLE_USER"))
			.build();
		save(user);
	}

	@Override
	public String login(Map<String, String> map) {
		String email = map.get("email");
		String password = map.get("password");
		User user = userRepository.findByEmail(email)
			.orElseThrow(() -> new IllegalArgumentException("가입되지 않은 이메일입니다."));
		if (!passwordEncoder.matches(password, user.getPassword())) {
			throw new IllegalArgumentException("잘못된 비밀번호입니다.");
		}
		return jwtProvider.createToken(email, user.getRoles());
	}

	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	@Transactional(readOnly = true)
	public User getUser(long uid) {
		return userRepository.findById(uid)
			.orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다."));
	}

}

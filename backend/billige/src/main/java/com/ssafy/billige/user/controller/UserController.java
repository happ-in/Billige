package com.ssafy.billige.user.controller;

import static com.ssafy.billige.utils.StringUtils.*;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.billige.user.dto.request.UserRequest;
import com.ssafy.billige.user.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(value = "api")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/join")
	public ResponseEntity<?> joinUs(@RequestBody UserRequest userRequest) {
		userService.joinUs(userRequest);
		return ResponseEntity.ok().body(SUCCESS);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody Map<String, String> map) {
		return ResponseEntity.ok().body(userService.login(map));
	}
}

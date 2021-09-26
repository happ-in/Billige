package com.ssafy.billige.user.service;

import java.util.Map;

import com.ssafy.billige.user.domain.User;
import com.ssafy.billige.user.dto.request.UserRequest;

public interface UserService {
	User getKakaoUser(User user);

	void joinUs(UserRequest userRequest);

	String login(Map<String, String> map);

	User getUser(long uid);
}

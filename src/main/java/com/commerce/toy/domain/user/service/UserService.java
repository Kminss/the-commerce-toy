package com.commerce.toy.domain.user.service;

import static com.commerce.toy.global.exception.ErrorCode.*;

import org.springframework.stereotype.Service;

import com.commerce.toy.domain.user.dto.request.JoinRequest;
import com.commerce.toy.domain.user.service.repository.UserRepository;
import com.commerce.toy.global.exception.ApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;
	public void join(JoinRequest request) {
		String id = request.getId();

		if (userRepository.existsUserById(id)) {
			throw new ApiException(ALREADY_EXIST_USER);
		}

		userRepository.save(request.toEntity());
	}
}

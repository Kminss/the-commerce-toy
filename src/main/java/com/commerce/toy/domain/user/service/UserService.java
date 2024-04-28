package com.commerce.toy.domain.user.service;

import static com.commerce.toy.global.exception.ErrorCode.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.commerce.toy.domain.user.dto.response.UserInfoResponse;
import com.commerce.toy.domain.user.entity.User;
import com.commerce.toy.domain.user.dto.request.JoinRequest;
import com.commerce.toy.domain.user.dto.request.UpdateUserRequest;
import com.commerce.toy.domain.user.dto.response.UpdateUserResponse;
import com.commerce.toy.domain.user.service.repository.UserRepository;
import com.commerce.toy.global.exception.ApiException;
import com.commerce.toy.global.exception.ErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {
	private final UserRepository userRepository;

	@Transactional
	public void join(JoinRequest request) {
		String id = request.getId();

		if (userRepository.existsUserById(id)) {
			throw new ApiException(ALREADY_EXIST_USER);
		}

		userRepository.save(request.toEntity());
	}

	@Transactional
	public UpdateUserResponse update(String id, UpdateUserRequest request) {
		User user = userRepository.findUserById(id)
			.orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

		user.updateInfo(
			request.getPassword(),
			request.getNickname(),
			request.getPhoneNumber(),
			request.getEmail()
		);
		return UpdateUserResponse.from(user);
	}

	@Transactional(readOnly = true)
	public Page<UserInfoResponse> findAll(Pageable pageable) {
		return userRepository.findAll(pageable)
			.map(UserInfoResponse::from);
	}
}

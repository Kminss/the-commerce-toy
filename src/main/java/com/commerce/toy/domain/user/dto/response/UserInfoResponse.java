package com.commerce.toy.domain.user.dto.response;

import java.time.LocalDateTime;

import com.commerce.toy.domain.user.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoResponse {
	private final String id;
	private final String nickname;
	private final String name;
	private final String phoneNumber;
	private final String email;
	private final LocalDateTime createdAt;

	public static UserInfoResponse from(User entity) {
		return new UserInfoResponse(
			entity.getId(),
			entity.getNickname(),
			entity.getName(),
			entity.getPhoneNumber(),
			entity.getEmail(),
			entity.getCreatedAt()
		);
	}
}

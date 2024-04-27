package com.commerce.toy.domain.user.dto.response;

import com.commerce.toy.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserResponse {
	private final String id;
	private final String nickname;
	private final String name;
	private final String phoneNumber;
	private final String email;

	public static UpdateUserResponse from(User entity) {
		return new UpdateUserResponse(
			entity.getId(),
			entity.getNickname(),
			entity.getName(),
			entity.getPhoneNumber(),
			entity.getEmail()
		);
	}
}

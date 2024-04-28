package com.commerce.toy.domain.user.dto.response;

import com.commerce.toy.domain.user.entity.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@ApiModel(value = "회원 수정 응답 DTO", description = "회원수정 응답 정보를 담은 DTO 입니다.")
@Getter
@AllArgsConstructor
public class UpdateUserResponse {
	@ApiModelProperty(value = "로그인 아이디")
	private final String id;

	@ApiModelProperty(value = "닉네임")
	private final String nickname;

	@ApiModelProperty(value = "이름")
	private final String name;

	@ApiModelProperty(value = "휴대전화번호")
	private final String phoneNumber;

	@ApiModelProperty(value = "이메일")
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

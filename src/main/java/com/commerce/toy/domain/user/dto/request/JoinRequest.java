package com.commerce.toy.domain.user.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.commerce.toy.domain.user.entity.User;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "회원가입 요청 DTO", description = "회원가입 정보를 담은 DTO 입니다.")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class JoinRequest {
	@ApiModelProperty(value = "로그인 아이디", required = true)
	@Size(
		min = 8,
		max = 30,
		message = "아이디는 8자리 이상, 30자리 이하로 입력해야합니다."
	)
	@NotBlank(message = "아이디 입력은 필수 입니다.")
	@Pattern(
		regexp = "^[a-z0-9]+$",
		message = "아이디는 알파벳 소문자, 숫자의 조합으로 입력해야합니다."
	)
	private String id; // 로그인 ID

	@ApiModelProperty(value = "비밀번호", required = true)
	@Pattern(
		regexp = "^[a-zA-Z0-9]+$",
		message = "비밀번호는 알파벳 대/소문자, 숫자의 조합으로 입력해야합니다."
	)
	@Size(
		min = 8,
		max = 20,
		message = "비밀번호는 8자리 이상, 20자리 이하로 입력해야합니다."
	)
	@NotBlank(message = "비밀번호 입력은 필수 입니다.")
	private String password; //비밀번호

	@ApiModelProperty(value = "닉네임", required = true)
	@Size(
		min = 3,
		max = 20,
		message = "닉네임은 3자리 이상, 20자리 이하로 입력해야합니다."
	)
	@NotBlank(message = "닉네임 입력은 필수 입니다.")
	private String nickname; //닉네임

	@ApiModelProperty(value = "이름", required = true)
	@Size(max = 30, message = "이름은 30자 이하로 입력해야합니다.")
	@NotBlank(message = "이름 입력은 필수 입니다.")
	private String name; //이름

	@ApiModelProperty(value = "휴대전화번호", required = true)
	@Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "유효하지 않은 휴대전화번호입니다.")
	@NotBlank(message = "휴대전화번호 입력은 필수 입니다.")
	private String phoneNumber; //휴대전화번호

	@ApiModelProperty(value = "이메일", required = true)
	@Pattern(
		regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$",
		message = "이메일 형식에 맞지 않습니다."
	)
	@NotBlank(message = "이메일 입력은 필수 입니다.")
	private String email; //이메일

	@Builder
	public JoinRequest(String id, String password, String nickname, String name, String phoneNumber, String email) {
		this.id = id;
		this.password = password;
		this.nickname = nickname;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	public User toEntity() {
		return User.of(id, password, nickname, name, phoneNumber, email);
	}
}

package com.commerce.toy.domain.user;

import lombok.Getter;

@Getter
public class User {

	private Long id;

	private String password; //비밀번호

	private String nickname; //닉네임

	private String phoneNumber; //휴대전화번호

	private String email; //이메일
}

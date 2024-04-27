package com.commerce.toy.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@Entity
public class User {

	@Id
	@Column(name = "id", length = 30)
	private String id; //회원 로그인 ID

	@Column(name = "password", nullable = false)
	private String password; //비밀번호

	@Column(name ="nickname", length = 50)
	private String nickname; //닉네임

	@Column(name ="name", length = 30)
	private String name; //이름

	@Column(name = "phone_number", length = 15)
	private String phoneNumber; //휴대전화번호

	@Column(name = "email", length = 100)
	private String email; //이메일

	private User(String id, String password, String nickname, String name, String phoneNumber, String email) {
		this.id = id;
		this.password = password;
		this.nickname = nickname;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.email = email;
	}

	public static User of(String id, String password, String nickname, String name, String phoneNumber, String email) {
		return new User(id, password, nickname, name, phoneNumber, email);
	}
}

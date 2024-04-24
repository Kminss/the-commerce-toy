package com.commerce.toy.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id; //회원 식별자 ID

	@Column(name = "password", nullable = false)
	private String password; //비밀번호

	@Column(name ="nickname", length = 50)
	private String nickname; //닉네임

	@Column(name = "phone_number", length = 15)
	private String phoneNumber; //휴대전화번호

	@Column(name = "email", length = 100)
	private String email; //이메일
}

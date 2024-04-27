package com.commerce.toy.domain.user.dto.request;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class JoinRequestTest {
	private static ValidatorFactory factory;
	private static Validator validator;

	@BeforeAll
	public static void init() {
		factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@AfterAll
	public static void close() {
		factory.close();
	}

	@DisplayName("비밀번호 유효성 검증")
	@Nested
	class PasswordTest {

		@DisplayName("한글 비밀번호 입력시 실패")
		@Test
		void givenKorPassword_whenRequestingJoin_thenReturn400() throws Exception {
			//Given
			JoinRequest request = new JoinRequest(
				"패스워드1234",
				"nickname",
				"name",
				"010-0000-0000",
				"test@test.com"
			);
			//When
			Set<ConstraintViolation<JoinRequest>> validations = validator.validate(request);
			//Then
			assertThat(validations).hasSize(1);
		}

		@DisplayName("특수문자 입력시 실패")
		@Test
		void givenSymbolPassword_whenRequestingJoin_thenReturn400() throws Exception {
			//Given
			JoinRequest request = new JoinRequest(
				"password1234@",
				"nickname",
				"name",
				"010-0000-0000",
				"test@test.com"
			);
			//When
			Set<ConstraintViolation<JoinRequest>> validations = validator.validate(request);
			//Then
			assertThat(validations).hasSize(1);
		}

		@DisplayName("패스워드 길이 기준 범위 밖 실패")
		@ValueSource(strings = {"passwor", "password1234567891011"})
		@ParameterizedTest
		void givenUnderLengthPassword_whenRequestingJoin_thenReturn400(String password) throws Exception {
			//Given
			JoinRequest request = new JoinRequest(
				password,
				"nickname",
				"name",
				"010-0000-0000",
				"test@test.com"
			);
			//When
			Set<ConstraintViolation<JoinRequest>> validations = validator.validate(request);
			//Then
			assertThat(validations).hasSize(1);
		}

		@DisplayName("패스워드 길이 기준 경계 정상")
		@ValueSource(strings = {"password", "password123456789101"})
		@ParameterizedTest
		void givenOverLengthPassword_whenRequestingJoin_thenReturn400(String password) throws Exception {
			//Given
			JoinRequest request = new JoinRequest(
				password,
				"nickname",
				"name",
				"010-0000-0000",
				"test@test.com"
			);
			//When
			Set<ConstraintViolation<JoinRequest>> validations = validator.validate(request);
			//Then
			assertThat(validations).isEmpty();
		}
	}

	@DisplayName("닉네임 유효성 검증")
	@Nested
	class NicknameTest {

		@DisplayName("닉네임 길이 기준 범위 밖 실패")
		@ValueSource(strings = {"ni", "nickname1234567890111"})
		@ParameterizedTest
		void givenOverRangeLengthNickname_whenRequestingJoin_thenReturn400(String nickname) throws Exception {
			//Given
			JoinRequest request = new JoinRequest(
				"password",
				nickname,
				"name",
				"010-0000-0000",
				"test@test.com"
			);
			//When
			Set<ConstraintViolation<JoinRequest>> validations = validator.validate(request);
			//Then
			assertThat(validations).hasSize(1);
		}

		@DisplayName("닉네임 길이 기준 경계 정상")
		@ValueSource(strings = {"nic", "nickname123456789011"})
		@ParameterizedTest
		void givenOverLengthNickname_whenRequestingJoin_thenReturn400(String nickname) throws Exception {
			//Given
			JoinRequest request = new JoinRequest(
				"password",
				nickname,
				"name",
				"010-0000-0000",
				"test@test.com"
			);
			//When
			Set<ConstraintViolation<JoinRequest>> validations = validator.validate(request);
			//Then
			assertThat(validations).isEmpty();
		}
	}

	@DisplayName("이메일 유효성 검증")
	@Nested
	class EmailTest {

		@DisplayName("이메일 유효성 실패")
		@ValueSource(strings = {"test.com", "tesst1_#@test.com.kr"})
		@ParameterizedTest
		void givenUnderLengthNickname_whenRequestingJoin_thenReturn400(String email) throws Exception {
			//Given
			JoinRequest request = new JoinRequest(
				"password",
				"nickname",
				"name",
				"010-0000-0000",
				email
			);

			//When
			Set<ConstraintViolation<JoinRequest>> validations = validator.validate(request);
			//Then
			assertThat(validations).hasSize(1);
		}
	}

	@DisplayName("휴대전화 유효성 검증")
	@Nested
	class PhoneNumberTest {

		@DisplayName("휴대전화 유효성 실패")
		@ValueSource(strings = {"0-102-1234", "0431-100-1234", "02-12-1234", "02-12345-123", "01012341234"})
		@ParameterizedTest
		void givenUnderLengthNickname_whenRequestingJoin_thenReturn400(String phoneNumber) throws Exception {
			//Given
			JoinRequest request = new JoinRequest(
				"password",
				"nickname",
				"name",
				phoneNumber,
				"test@test.com"
			);

			//When
			Set<ConstraintViolation<JoinRequest>> validations = validator.validate(request);
			//Then
			assertThat(validations).hasSize(1);
		}
	}
}
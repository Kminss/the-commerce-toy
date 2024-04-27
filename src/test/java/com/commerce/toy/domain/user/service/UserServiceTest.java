package com.commerce.toy.domain.user.service;

import static com.commerce.toy.global.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import com.commerce.toy.domain.user.User;
import com.commerce.toy.domain.user.dto.request.JoinRequest;
import com.commerce.toy.domain.user.dto.request.UpdateUserRequest;
import com.commerce.toy.domain.user.dto.response.UpdateUserResponse;
import com.commerce.toy.domain.user.service.repository.UserRepository;
import com.commerce.toy.global.exception.ApiException;

@DisplayName("서비스 테스트 - User")
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@InjectMocks
	private UserService sut;
	@Mock
	private UserRepository userRepository;

	@DisplayName("회원가입 성공")
	@Test
	void givenJoinInfo_whenRequestingJoin_thenCreateUserAndReturn201() throws Exception {
		//Given
		JoinRequest request = JoinRequest.builder()
			.id("testid")
			.email("test1@test.com")
			.password("testPassword")
			.nickname("nickname")
			.phoneNumber("010-0000-0000")
			.build();
		User user = request.toEntity();

		given(userRepository.existsUserById(request.getId())).willReturn(false);
		//When
		sut.join(request);

		//Then
		then(userRepository).should().save(any(User.class));
	}

	@DisplayName("회원가입 실패 - 아이디가 중복되는 경우")
	@Test
	@Transactional
	void givenDuplicateJoinInfo_whenRequestingJoin_thenReturn409() throws Exception {
		//Given
		JoinRequest request = JoinRequest.builder()
			.id("testids")
			.email("test@test.com")
			.password("testPassword")
			.nickname("nickname")
			.phoneNumber("010-0000-0000")
			.build();

		given(userRepository.existsUserById(request.getId())).willReturn(true);

		//When && Then
		assertThatThrownBy(() -> sut.join(request))
			.isInstanceOf(ApiException.class)
			.hasMessage(ALREADY_EXIST_USER.getMessage());
	}

	@DisplayName("회원 수정 성공")
	@Test
	@Transactional
	void givenUpdateUserInfo_whenRequestingUpdate_thenReturnUpdatedUserInfo() throws Exception {
		//Given
		String id = "testidss";
		UpdateUserRequest request = UpdateUserRequest.builder()
			.password("testpassword")
			.nickname("testnickname")
			.phoneNumber("010-1234-5678")
			.email("testemail@email.com")
			.build();

		User user = User.of(
			id,
			"password1234",
			"nicknames",
			"name",
			"010-0000-0000",
			"test@test.com"
		);

		given(userRepository.findUserById(id)).willReturn(Optional.of(user));

		//When
		UpdateUserResponse response = sut.update(id, request);

		//Then
		assertThat(response.getId()).isEqualTo(id);
		assertThat(response.getNickname()).isEqualTo(request.getNickname());
		assertThat(response.getPhoneNumber()).isEqualTo(request.getPhoneNumber());
		assertThat(response.getEmail()).isEqualTo(request.getEmail());
	}

	@DisplayName("회원수정 실패 - 아이디에 해당되는 회원을 찾을 수 없는 경우")
	@Test
	@Transactional
	void givenNoUserIdAndUpdateInfo_whenRequestingUpdate_thenReturn404() throws Exception {
		//Given
		String id = "testidss";
		UpdateUserRequest request = UpdateUserRequest.builder()
			.password("testpassword")
			.nickname("testnickname")
			.phoneNumber("010-1234-5678")
			.email("testemail@email.com")
			.build();

		//When && Then
		assertThatThrownBy(() -> sut.update(id, request))
			.isInstanceOf(ApiException.class)
			.hasMessage(USER_NOT_FOUND.getMessage());
	}
}
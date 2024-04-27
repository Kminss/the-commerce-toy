package com.commerce.toy.domain.user.service;

import static com.commerce.toy.global.exception.ErrorCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.transaction.annotation.Transactional;

import com.commerce.toy.domain.user.User;
import com.commerce.toy.domain.user.dto.request.JoinRequest;
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
			.email("test1@test.com")
			.password("testPassword")
			.nickname("nickname")
			.phoneNumber("010-0000-0000")
			.build();
		User user = request.toEntity();

		given(userRepository.existsUserByEmail(request.getEmail())).willReturn(false);
		//When
		sut.join(request);

		//Then
		then(userRepository).should().save(any(User.class));
	}

	@DisplayName("회원가입 실패 - 이메일 중복되는 경우")
	@Test
	@Transactional
	void givenDuplicateJoinInfo_whenRequestingJoin_thenReturn409() throws Exception {
		//Given
		JoinRequest request = JoinRequest.builder()
			.email("test@test.com")
			.password("testPassword")
			.nickname("nickname")
			.phoneNumber("010-0000-0000")
			.build();

		given(userRepository.existsUserByEmail(request.getEmail())).willReturn(true);

		//When && Then
		assertThatThrownBy(() -> sut.join(request))
			.isInstanceOf(ApiException.class)
			.hasMessage(ALREADY_EXIST_USER.getMessage());
	}
}
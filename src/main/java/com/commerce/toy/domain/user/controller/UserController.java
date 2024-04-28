package com.commerce.toy.domain.user.controller;

import static org.springframework.http.HttpStatus.*;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.toy.domain.user.dto.request.JoinRequest;
import com.commerce.toy.domain.user.dto.request.UpdateUserRequest;
import com.commerce.toy.domain.user.dto.response.UpdateUserResponse;
import com.commerce.toy.domain.user.dto.response.UserInfoResponse;
import com.commerce.toy.domain.user.service.UserService;
import com.commerce.toy.global.response.ApiResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

@Api(tags = {"회원가입 API 컨트롤러"})
@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {

	private final UserService userService;

	@ApiOperation(value = "회원가입 API", notes = "회원가입을 수행합니다.")
	@ApiResponses({
		@io.swagger.annotations.ApiResponse(code=201, message="회원가입 완료"),
		@io.swagger.annotations.ApiResponse(
			code=409,
			message="이미 중복된 아이디가 존재하는 경우",
			response = ApiResponse.class)
	})
	@PostMapping("/join")
	public ResponseEntity<Void> join(@RequestBody @Valid JoinRequest request) {
		userService.join(request);
		return ResponseEntity.status(CREATED).build();
	}

	@ApiOperation(value = "회원 수정 API", notes = "회원 정보 수정을 수행합니다.")
	@ApiImplicitParam(name = "id", value = "회원 로그인 ID", dataType = "String", paramType = "Path")
	@ApiResponses({
		@io.swagger.annotations.ApiResponse(code=200, message="회원 수정 완료"),
		@io.swagger.annotations.ApiResponse(code=404, message="수정할 회원이 없는 경우", response = ApiResponse.class)
	})
	@PutMapping("/update/{id}")
	public ResponseEntity<ApiResponse<UpdateUserResponse>> update(
		@PathVariable String id,
		@RequestBody @Valid UpdateUserRequest request
	) {
		UpdateUserResponse response = userService.update(id, request);
		return ResponseEntity.ok(ApiResponse.of("회원 수정 성공", response));
	}

	@ApiOperation(value = "회원 목록 조회 API", notes = "회원정보 목록을 조회합니다.")
	@ApiResponses({
		@io.swagger.annotations.ApiResponse(code=200, message="회원 목록 조회 완료"),
	})
	@GetMapping("/list")
	public ResponseEntity<ApiResponse<Page<UserInfoResponse>>> findAll(
		@PageableDefault(size = 25, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<UserInfoResponse> response = userService.findAll(pageable);
		return ResponseEntity.ok(ApiResponse.of("회원 목록 조회 성공", response));
	}
}

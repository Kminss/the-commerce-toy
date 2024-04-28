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

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/user")
@RestController
public class UserController {

	private final UserService userService;

	@PostMapping("/join")
	public ResponseEntity<ApiResponse<Void>> join(@RequestBody @Valid JoinRequest request) {
		userService.join(request);
		return ResponseEntity.status(CREATED).build();
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<ApiResponse<UpdateUserResponse>> update(
		@PathVariable String id,
		@RequestBody @Valid UpdateUserRequest request
	) {
		UpdateUserResponse response = userService.update(id, request);
		return ResponseEntity.ok(ApiResponse.of("회원 수정 성공", response));
	}

	@GetMapping("/list")
	public ResponseEntity<ApiResponse<Page<UserInfoResponse>>> findAll(
		@PageableDefault(size = 25, sort = "createdAt", direction = Sort.Direction.DESC)
		Pageable pageable
	) {
		Page<UserInfoResponse> response = userService.findAll(pageable);
		return ResponseEntity.ok(ApiResponse.of("회원 목록 조회 성공", response));
	}
}

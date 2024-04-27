package com.commerce.toy.domain.user.controller;

import static org.springframework.http.HttpStatus.*;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.commerce.toy.domain.user.dto.request.JoinRequest;
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
}

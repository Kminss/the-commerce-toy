package com.commerce.toy.global.exception;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonSubTypes;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	//400 BadRequest
	INVALID_REQUEST(BAD_REQUEST, "유효하지 않은 요청입니다."),

	//404 NotFound
	USER_NOT_FOUND(NOT_FOUND, "회원을 찾을 수 없습니다."),

	//409 Conflict
	ALREADY_EXIST_USER(CONFLICT, "이미 존재하는 회원입니다."),

	//500 Iternal Server Error
	SERVER_ERROR(INTERNAL_SERVER_ERROR, "API 서버 에러입니다.");

	private HttpStatus httpStatus;
	private String message;
}

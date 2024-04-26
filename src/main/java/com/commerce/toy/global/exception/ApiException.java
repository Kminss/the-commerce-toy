package com.commerce.toy.global.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {

	private final HttpStatus httpStatus;
	private final String message;

	public ApiException(ErrorCode errorCode) {
		this.httpStatus = errorCode.getHttpStatus();
		this.message = errorCode.getMessage();
	}

}
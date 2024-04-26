package com.commerce.toy.global.response;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

	private String message;
	private T data;

	public static <K> ApiResponse<K> of( String message, K data) {
		return new ApiResponse<>(message, data);
	}

	public static  ApiResponse<Void> of(String message) {
		return new ApiResponse<>(message, null);
	}
}

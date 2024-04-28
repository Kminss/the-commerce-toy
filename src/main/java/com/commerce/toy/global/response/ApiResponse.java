package com.commerce.toy.global.response;

import java.util.Objects;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@ApiModel(value = "공통 응답 DTO", description = "API 공통 응답 DTO 입니다.")
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

	@ApiModelProperty(value = "응답 메세지")
	private String message;

	@ApiModelProperty(value = "응답 데이터")
	private T data;

	public static <K> ApiResponse<K> of( String message, K data) {
		return new ApiResponse<>(message, data);
	}

	public static  ApiResponse<Void> of(String message) {
		return new ApiResponse<>(message, null);
	}
}

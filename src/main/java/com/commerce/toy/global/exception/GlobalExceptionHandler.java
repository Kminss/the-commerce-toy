package com.commerce.toy.global.exception;

import static com.commerce.toy.global.exception.ErrorCode.*;

import java.util.HashMap;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.commerce.toy.global.response.ApiResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	/**
	 * [Exception] RuntimeException 반환하는 경우
	 *
	 * @param ex RuntimeException
	 * @return ResponseEntity<ErrorResponse>
	 */
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiResponse<Void>> runtimeExceptionHandler(RuntimeException ex) {
		log.error("Runtime exceptions:", ex);
		return ResponseEntity.status(SERVER_ERROR.getHttpStatus())
			.body(ApiResponse.of(SERVER_ERROR.getMessage()));
	}

	/**
	 * [Exception] API 요청 시 '객체' 혹은 '파라미터' 데이터 값이 유효하지 않은 경우
	 *
	 * @param ex MethodArgumentNotValidException,
	 * @return ResponseEntity<ErrorResponse>
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {
		log.error("handleMethodArgumentNotValidException", ex);
		BindingResult bindingResult = ex.getBindingResult();
		HashMap<String, String> errors = new HashMap<>();
		bindingResult.getAllErrors()
			.forEach(error -> errors.put(((FieldError)error).getField(), error.getDefaultMessage()));

		return ResponseEntity.status(INVALID_REQUEST.getHttpStatus())
			.body(ApiResponse.of(INVALID_REQUEST.getMessage(), errors));
	}

	/**
	 * [Exception] API 요청에 맞는 파라미터를 받지 못한 경우
	 *
	 * @param ex MissingServletRequestParameterException,
	 * @return ResponseEntity<ErrorResponse>
	 */
	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
		HttpHeaders headers, HttpStatus status, WebRequest request) {

		return ResponseEntity.status(INVALID_REQUEST.getHttpStatus())
			.body(ApiResponse.of("잘못된 요청입니다." + "누락된 파라미터: " + ex.getParameterName()));
	}

	/**
	 * [Exception] API 호출 시 CustomException 으로 정의한 예외가 반환되는 경우
	 *
	 * @param ex MethodArgumentNotValidException
	 * @return ResponseEntity<ApiResponse<Void>>
	 */
	@ExceptionHandler(value = {ApiException.class})
	protected ResponseEntity<ApiResponse<Void>> handleCustomException(ApiException ex) {
		HttpStatus status = ex.getHttpStatus();
		String message = ex.getMessage();
		log.error("handleCustomException throw CustomException : HttpStatus : {}, Message : {}", status, message);
		return ResponseEntity.status(status).body(ApiResponse.of(message));
	}
}
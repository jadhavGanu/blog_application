package com.project.blogging.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import com.project.blogging.payload.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// resourceNotFoundException is custom exception
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
		String message = ex.getMessage();

		ApiResponse apiResponse = new ApiResponse(message, false);

		return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handlerMethodArgumentNotValidException(
			MethodArgumentNotValidException ex) {
		Map<String, String> resp = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach(error -> {

			String fieldName =((FieldError)error).getField();
			String message = error.getDefaultMessage();
			resp.put(fieldName, message);

		});

		return new ResponseEntity<Map<String, String>>(resp, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InvalidUserNameOrPasswordException.class)
	public ResponseEntity<ApiResponse> handleInvalidPasswordException(InvalidUserNameOrPasswordException ex) {
		String message = ex.getMessage();

		ApiResponse apiResponse = new ApiResponse(message, false);

		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}
}

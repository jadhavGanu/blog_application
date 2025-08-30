package com.project.blogging.exception;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException.BadRequest;
import org.springframework.web.context.request.WebRequest;

import com.project.blogging.payload.ApiResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

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
	
	@ExceptionHandler(AccessDeniedException.class)
	protected ResponseEntity<Object> handleHasPermission(AccessDeniedException ex, WebRequest request) {
		Map<String, Object> body = new LinkedHashMap<String, Object>();
		body.put("code", HttpStatus.NOT_ACCEPTABLE.value());
        body.put("msg", "You do not have access for it.");
        return new ResponseEntity<>(body, HttpStatus.OK);
	}
	
//	@ExceptionHandler(RuntimeException.class)
//	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//	public ResponseEntity<Object> handleAllUncaughtRuntimeException(RuntimeException exception, WebRequest request) {
////		log.error("RuntimeException, ", exception);
//		Map<String, Object> body = new LinkedHashMap<String, Object>();
//		body.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
//        body.put("msg", "Something is wrong, try again!");
//		return new ResponseEntity<>(body, HttpStatus.OK);
//	}
//	
//	@ExceptionHandler(Exception.class)
//	public ResponseEntity<Object> handleAllUncaughtException(Exception exception, WebRequest request) {
////		log.error("Exception, ", exception);
//		Map<String, Object> body = new LinkedHashMap<String, Object>();
//		body.put("code", HttpStatus.INTERNAL_SERVER_ERROR.value());
//        body.put("msg", "Something is wrong, try again!");
//		return new ResponseEntity<>(body, HttpStatus.OK);
//	}
	
//	@ExceptionHandler(InvalidJwtException.class)
//    public ResponseEntity<String> handleInvalidJwtException(InvalidJwtException ex) {
//        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
//    }
//
//    @ExceptionHandler(io.jsonwebtoken.security.SignatureException.class)
//    public ResponseEntity<Object> handleJwtSignatureException(io.jsonwebtoken.security.SignatureException ex) {
//        String message = ex.getMessage();
//        
//		Map<String, Object> body = new LinkedHashMap<String, Object>();
//		body.put("code", HttpStatus.UNAUTHORIZED.value());
//        body.put("msg", message);
//        return new ResponseEntity<Object>(body, HttpStatus.UNAUTHORIZED );
//    }
	
	 // Handle JWT Signature Exception
    @ExceptionHandler(SignatureException.class)
    public ResponseEntity<Map<String, String>> handleSignatureException(SignatureException ex) {
        return createErrorResponse("Invalid JWT token: Signature does not match!", HttpStatus.UNAUTHORIZED);
    }

    // Handle Expired JWT Exception
    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, String>> handleExpiredJwtException(ExpiredJwtException ex) {
        return createErrorResponse("JWT token has expired!", HttpStatus.UNAUTHORIZED);
    }

    // Handle Malformed JWT Exception
    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<Map<String, String>> handleMalformedJwtException(MalformedJwtException ex) {
        return createErrorResponse("Invalid JWT token format!", HttpStatus.BAD_REQUEST);
    }

    // Generic Exception Handler
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<Map<String, String>> handleGenericException(Exception ex) {
//        return createErrorResponse("Something went wrong: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//    }

    // Utility method to generate JSON error response
    private ResponseEntity<Map<String, String>> createErrorResponse(String message, HttpStatus status) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", message);
        return new ResponseEntity<>(errorResponse, status);
    }
}



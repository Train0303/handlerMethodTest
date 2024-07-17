package org.example.handlermethodtest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CustomExceptionHandler {
	@ExceptionHandler(Exception.class)
	public String handleException(Exception e) {
		return e.getMessage();
	}
}

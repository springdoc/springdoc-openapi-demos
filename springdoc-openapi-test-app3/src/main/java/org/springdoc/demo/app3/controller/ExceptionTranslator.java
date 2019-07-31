package org.springdoc.demo.app3.controller;

import org.springdoc.demo.app3.exception.TweetNotFoundException;
import org.springdoc.demo.app3.payload.ErrorResponse;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionTranslator {


	@SuppressWarnings("rawtypes")
	@ExceptionHandler(DuplicateKeyException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public ResponseEntity handleDuplicateKeyException(DuplicateKeyException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(new ErrorResponse("A Tweet with the same text already exists"));
	}

	@SuppressWarnings("rawtypes")
	@ExceptionHandler(TweetNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity handleTweetNotFoundException(TweetNotFoundException ex) {
		return ResponseEntity.notFound().build();
	}

}

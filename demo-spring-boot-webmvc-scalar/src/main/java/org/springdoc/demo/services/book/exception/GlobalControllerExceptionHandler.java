/*
 *
 *  * Copyright 2019-2020 the original author or authors.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      https://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package org.springdoc.demo.services.book.exception;

import java.time.Instant;

import org.springframework.core.convert.ConversionFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ConversionFailedException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ProblemDetail handleConnversion(RuntimeException e) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
		problemDetail.setTitle("Bookmark is Not Found");
		problemDetail.setProperty("errorCategory", "Generic Exception");
		problemDetail.setProperty("timestamp", Instant.now());
		return problemDetail;
	}

	@ExceptionHandler(BookNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ProblemDetail handleBookNotFound(RuntimeException e) {
		ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
		problemDetail.setTitle("Book Not Found");
		problemDetail.setProperty("errorCategory", "Generic Exception");
		problemDetail.setProperty("timestamp", Instant.now());
		return problemDetail;
	}
}

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

package org.springdoc.demo.services.book.controller;

import java.util.Collection;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springdoc.demo.services.book.exception.BookNotFoundException;
import org.springdoc.demo.services.book.model.Book;
import org.springdoc.demo.services.book.repository.BookRepository;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * The type Book controller.
 */
@RestController
@RequestMapping("/api/book")
public class BookController {

	/**
	 * The Repository.
	 */
	private final BookRepository repository;

	/**
	 * Instantiates a new Book controller.
	 *
	 * @param repository the repository
	 */
	public BookController(BookRepository repository) {
		this.repository = repository;
	}

	/**
	 * Find by id book.
	 *
	 * @param id the id
	 * @return the book
	 */
	@GetMapping("/{id}")
	public Book findById(@PathVariable long id) {
		return repository.findById(id)
				.orElseThrow(() -> new BookNotFoundException());
	}

	/**
	 * Find books collection.
	 *
	 * @return the collection
	 */
	@GetMapping("/")
	public Collection<Book> findBooks() {
		return repository.getBooks();
	}

	/**
	 * Update book book.
	 *
	 * @param id   the id
	 * @param book the book
	 * @return the book
	 */
	@PutMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Book updateBook(@PathVariable("id") final String id, @RequestBody final Book book) {
		return book;
	}

	/**
	 * Patch book book.
	 *
	 * @param id   the id
	 * @param book the book
	 * @return the book
	 */
	@PatchMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public Book patchBook(@PathVariable("id") final String id, @RequestBody final Book book) {
		return book;
	}

	/**
	 * Post book book.
	 *
	 * @param book the book
	 * @return the book
	 */
	@PostMapping("/")
	@ResponseStatus(HttpStatus.CREATED)
	public Book postBook(@NotNull @Valid @RequestBody final Book book) {
		return book;
	}

	/**
	 * Head book book.
	 *
	 * @return the book
	 */
	@RequestMapping(method = RequestMethod.HEAD, value = "/")
	@ResponseStatus(HttpStatus.OK)
	public Book headBook() {
		return new Book();
	}

	/**
	 * Delete book long.
	 *
	 * @param id the id
	 * @return the long
	 */
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	public long deleteBook(@PathVariable final long id) {
		return id;
	}
}

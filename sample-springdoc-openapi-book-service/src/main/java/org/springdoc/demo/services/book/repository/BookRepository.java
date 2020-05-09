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

package org.springdoc.demo.services.book.repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springdoc.demo.services.book.model.Book;

import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {

	private Map<Long, Book> books = new HashMap<>();

	public Optional<Book> findById(long id) {
		return Optional.ofNullable(books.get(id));
	}

	public void add(Book book) {
		books.put(book.getId(), book);
	}

	public Collection<Book> getBooks() {
		return books.values();
	}
}

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

package org.springdoc.demo.services.book.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * The type Book.
 */
public class Book {

	/**
	 * The Id.
	 */
	private long id;

	/**
	 * The Title.
	 */
	@NotBlank
	@Size(min = 0, max = 20)
	private String title;

	/**
	 * The Author.
	 */
	@NotBlank
	@Size(min = 0, max = 30)
	private String author;

	/**
	 * Gets id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets id.
	 *
	 * @param id the id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets title.
	 *
	 * @param title the title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets author.
	 *
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * Sets author.
	 *
	 * @param author the author
	 */
	public void setAuthor(String author) {
		this.author = author;
	}
}

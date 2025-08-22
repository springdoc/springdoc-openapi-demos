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

package org.springdoc.demo.app2.api;

import java.util.List;
import java.util.Optional;

import org.springdoc.demo.app2.model.User;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * A delegate to be called by the {@link UserApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */

public interface UserApiDelegate {

	default Optional<NativeWebRequest> getRequest() {
		return Optional.empty();
	}

	/**
	 * @see UserApi#createUser
	 */
	default ResponseEntity<Void> createUser(User user) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

	}

	/**
	 * @see UserApi#createUsersWithArrayInput
	 */
	default ResponseEntity<Void> createUsersWithArrayInput(List<User> user) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

	}

	/**
	 * @see UserApi#createUsersWithListInput
	 */
	default ResponseEntity<Void> createUsersWithListInput(List<User> user) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

	}

	/**
	 * @see UserApi#deleteUser
	 */
	default ResponseEntity<Void> deleteUser(String username) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

	}

	/**
	 * @see UserApi#getUserByName
	 */
	default ResponseEntity<User> getUserByName(String username) {
		getRequest().ifPresent(request -> {
			for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
				if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
					ApiUtil.setExampleResponse(request, "application/json", "{  \"firstName\" : \"firstName\",  \"lastName\" : \"lastName\",  \"password\" : \"password\",  \"userStatus\" : 6,  \"phone\" : \"phone\",  \"id\" : 0,  \"email\" : \"email\",  \"username\" : \"username\"}");
					break;
				}
				if (mediaType.isCompatibleWith(MediaType.valueOf("application/xml"))) {
					ApiUtil.setExampleResponse(request, "application/xml", "<User>  <id>123456789</id>  <username>aeiou</username>  <firstName>aeiou</firstName>  <lastName>aeiou</lastName>  <email>aeiou</email>  <password>aeiou</password>  <phone>aeiou</phone>  <userStatus>123</userStatus></User>");
					break;
				}
			}
		});
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

	}

	/**
	 * @see UserApi#loginUser
	 */
	default ResponseEntity<String> loginUser(String username,
			String password) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

	}

	/**
	 * @see UserApi#logoutUser
	 */
	default ResponseEntity<Void> logoutUser() {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

	}

	/**
	 * @see UserApi#updateUser
	 */
	default ResponseEntity<Void> updateUser(String username,
			User user) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

	}

}

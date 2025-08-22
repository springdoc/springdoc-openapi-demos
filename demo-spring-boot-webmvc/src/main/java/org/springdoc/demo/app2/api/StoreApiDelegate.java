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

import java.util.Map;
import java.util.Optional;

import org.springdoc.demo.app2.model.Order;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * A delegate to be called by the {@link StoreApiController}}.
 * Implement this interface with a {@link org.springframework.stereotype.Service} annotated class.
 */
public interface StoreApiDelegate {

	default Optional<NativeWebRequest> getRequest() {
		return Optional.empty();
	}

	/**
	 * @see StoreApi#deleteOrder
	 */
	default ResponseEntity<Void> deleteOrder(Long orderId) {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

	}

	/**
	 * @see StoreApi#getInventory
	 */
	default ResponseEntity<Map<String, Integer>> getInventory() {
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

	}

	/**
	 * @see StoreApi#getOrderById
	 */
	default ResponseEntity<Order> getOrderById(Long orderId) {
		extract();
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

	}

	default void extract() {
		getRequest().ifPresent(request -> {
			for (MediaType mediaType : MediaType.parseMediaTypes(request.getHeader("Accept"))) {
				if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
					ApiUtil.setExampleResponse(request, "application/json", "{  \"petId\" : 6,  \"quantity\" : 1,  \"id\" : 0,  \"shipDate\" : \"2000-01-23T04:56:07.000+00:00\",  \"complete\" : false,  \"status\" : \"placed\"}");
					break;
				}
				if (mediaType.isCompatibleWith(MediaType.valueOf("application/xml"))) {
					ApiUtil.setExampleResponse(request, "application/xml", "<Order>  <id>123456789</id>  <petId>123456789</petId>  <quantity>123</quantity>  <shipDate>2000-01-23T04:56:07.000Z</shipDate>  <status>aeiou</status>  <complete>true</complete></Order>");
					break;
				}
			}
		});
	}

	/**
	 * @see StoreApi#placeOrder
	 */
	default ResponseEntity<Order> placeOrder(Order order) {
		extract();
		return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

	}

}

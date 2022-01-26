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

package org.springdoc.demo.app3.controller;

import org.springdoc.demo.app3.dto.TweetDTO;
import org.springdoc.demo.app3.model.Tweet;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class TweetMapper {

	Flux<TweetDTO> toDTO(Flux<Tweet> tweet) {
		return tweet.map(this::toDTO);
	}

	Flux<Tweet> toEntity(Flux<TweetDTO> tweetDTO) {
		return tweetDTO.map(this::toEntity);
	}

	Mono<TweetDTO> toDTO(Mono<Tweet> tweet) {
		return tweet.map(this::toDTO);
	}

	Mono<Tweet> toEntity(Mono<TweetDTO> tweetDTO) {
		return tweetDTO.map(this::toEntity);
	}

	TweetDTO toDTO(Tweet tweet) {
		TweetDTO teTweetDTO = new TweetDTO();
		BeanUtils.copyProperties(teTweetDTO, tweet);
		return teTweetDTO;
	}

	Tweet toEntity(TweetDTO tweetDTO) {
		Tweet teTweet = new Tweet();
		BeanUtils.copyProperties(new Tweet(), tweetDTO);
		return teTweet;
	}

}

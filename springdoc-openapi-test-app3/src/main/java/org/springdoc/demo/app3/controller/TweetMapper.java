package org.springdoc.demo.app3.controller;

import org.springdoc.demo.app3.dto.TweetDTO;
import org.springdoc.demo.app3.model.Tweet;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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

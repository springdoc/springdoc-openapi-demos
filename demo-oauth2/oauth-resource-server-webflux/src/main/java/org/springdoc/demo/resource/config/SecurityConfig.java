package org.springdoc.demo.resource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityWebFilterChain configure(ServerHttpSecurity http) {

		http
				.authorizeExchange().pathMatchers("/v3/api-docs/**", "/swagger-ui/**", "/webjars/**", "/swagger-ui.html").permitAll()
				.pathMatchers(HttpMethod.GET, "/user/info", "/api/foos/**")
				.hasAuthority("SCOPE_read")
				.pathMatchers(HttpMethod.POST, "/api/foos")
				.hasAuthority("SCOPE_write")
				.anyExchange().authenticated().and().oauth2ResourceServer().jwt();


		return http.build();
	}

}
package org.springdoc.demo.resource.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {// @formatter:off
		http
				.authorizeRequests()
				.antMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
				.antMatchers(HttpMethod.GET, "/user/info", "/api/foos/**")
				.hasAuthority("SCOPE_read")
				.antMatchers(HttpMethod.POST, "/api/foos")
				.hasAuthority("SCOPE_write")
				.anyRequest()
				.authenticated()
				.and()
				.oauth2ResourceServer()
				.jwt();
	}

}
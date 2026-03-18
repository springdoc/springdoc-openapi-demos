package org.springdoc.demo.mcp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springaicommunity.mcp.security.server.config.McpServerOAuth2Configurer.mcpServerOAuth2;

@Configuration
@EnableMethodSecurity
@Profile("!stdout")
class McpSecurityConfiguration {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http,
			@Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}") String issuerUrl) throws Exception {
		return http.authorizeHttpRequests(auth -> auth
				.requestMatchers("/sse", "/mcp/message").permitAll()
				.anyRequest().permitAll())
			.with(mcpServerOAuth2(), (mcpAuthorization) -> {
				mcpAuthorization.authorizationServer(issuerUrl).resourcePath("/mcp");
			})
			.csrf(CsrfConfigurer::disable)
			.build();
	}

}

package org.springdoc.demo.app3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.adapter.ForwardedHeaderTransformer;

/**
 * The proxy in front of the demos sends the public host in the standard {@code Forwarded}
 * header, because Cloud Run strips {@code X-Forwarded-Host}, and the path prefix in
 * {@code X-Forwarded-Prefix}. Spring Boot reads {@code Forwarded} with the {@code rfc-7239}
 * naming but only applies the prefix with the {@code x-forwarded} naming, so this
 * transformer, which replaces the one of Spring Boot, reads both.
 *
 * @author bnasslahsen
 */
@Configuration(proxyBeanMethods = false)
class ForwardedHeadersConfiguration {

	@Bean
	ForwardedHeaderTransformer forwardedHeaderTransformer() {
		ForwardedHeaderTransformer transformer = new ForwardedHeaderTransformer(true);
		transformer.setUseForwardedPrefix(true);
		return transformer;
	}

}

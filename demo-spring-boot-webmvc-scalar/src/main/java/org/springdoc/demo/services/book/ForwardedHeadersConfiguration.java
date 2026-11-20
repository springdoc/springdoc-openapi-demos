package org.springdoc.demo.services.book;

import jakarta.servlet.DispatcherType;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.filter.ForwardedHeaderFilter;

/**
 * The proxy in front of the demos sends the public host in the standard {@code Forwarded}
 * header, because Cloud Run strips {@code X-Forwarded-Host}, and the path prefix in
 * {@code X-Forwarded-Prefix}. Spring Boot reads {@code Forwarded} with the {@code rfc-7239}
 * naming but only applies the prefix with the {@code x-forwarded} naming, so this filter,
 * registered the way Spring Boot registers its own, reads both.
 *
 * @author bnasslahsen
 */
@Configuration(proxyBeanMethods = false)
class ForwardedHeadersConfiguration {

	@Bean
	FilterRegistrationBean<ForwardedHeaderFilter> forwardedHeaderFilter() {
		ForwardedHeaderFilter filter = new ForwardedHeaderFilter(true);
		filter.setUseForwardedPrefix(true);
		FilterRegistrationBean<ForwardedHeaderFilter> registration = new FilterRegistrationBean<>(filter);
		registration.setDispatcherTypes(DispatcherType.REQUEST, DispatcherType.ASYNC, DispatcherType.ERROR);
		registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return registration;
	}

}

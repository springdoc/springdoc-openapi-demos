package org.springdoc.demo.services.person.config;

import javax.annotation.PostConstruct;
import javax.money.MonetaryAmount;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.javamoney.moneta.Money;
import org.zalando.jackson.datatype.money.MoneyModule;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.springdoc.core.SpringDocUtils.getConfig;

@Configuration
public class SampleConfig {

	@PostConstruct
	public void initConfig() {
		getConfig().replaceWithClass(MonetaryAmount.class,
				org.springdoc.core.converters.models.MonetaryAmount.class);
	}

	@Bean
	public OpenAPI customOpenAPI(BuildProperties buildProperties) {
		return new OpenAPI()
				.info(new Info()
						.title("sample application API")
						.version(buildProperties.getVersion())
						.description(buildProperties.getName())
						.termsOfService("http://swagger.io/terms/")
						.license(new License().name("Apache 2.0").url("http://springdoc.org")));
	}

	@Bean
	public MoneyModule moneyModule() {
		return new MoneyModule().withMonetaryAmount(Money::of);
	}
}

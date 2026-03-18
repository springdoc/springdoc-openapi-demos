package org.springdoc.demo.mcp.authorizationserver;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnabledIfSystemProperty(named = "mcp.auth.server.test", matches = "true")
class ApplicationTests {

	@Test
	void contextLoads() {
	}

}

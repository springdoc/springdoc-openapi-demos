package org.springdoc.demo.resource;

import java.util.HashMap;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

//Before running this live test make sure both authorization server and first resource server are running

public class PasswordFlowLiveTest {

	private final static String AUTH_SERVER = "http://localhost:8083/auth/realms/springdoc/protocol/openid-connect";
	private final static String RESOURCE_SERVER = "http://localhost:8081/resource-server";
	private final static String CLIENT_ID = "newClient";
	private final static String CLIENT_SECRET = "newClientSecret";
	private final static String USERNAME = "josh@test.com";
	private final static String PASSWORD = "123";

	@Test
	public void givenUser_whenUseFooClient_thenOkForFooResourceOnly() {
		final String accessToken = obtainAccessToken(CLIENT_ID, USERNAME, PASSWORD);

		final Response fooResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken)
				.get(RESOURCE_SERVER + "/api/foos/1");
		Assertions.assertEquals(200, fooResponse.getStatusCode());
		Assertions.assertNotNull(fooResponse.jsonPath().get("name"));
	}

	private String obtainAccessToken(String clientId, String username, String password) {
		final Map<String, String> params = new HashMap<String, String>();
		params.put("grant_type", "password");
		params.put("client_id", clientId);
		params.put("username", username);
		params.put("password", password);
		params.put("scope", "oidc read write");
		final Response response = RestAssured.given().auth().preemptive().basic(clientId, CLIENT_SECRET).and()
				.with().params(params).when().post(AUTH_SERVER + "/token");
		return response.jsonPath().getString("access_token");
	}

}

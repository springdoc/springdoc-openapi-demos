package org.springdoc.demo.resource;

import java.util.HashMap;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

//Before running this live test make sure both authorization server and first resource server are running   

public class ImplicitFlowLiveTest {
	private final static String AUTH_SERVER = "http://localhost:8083/auth/realms/springdoc/protocol/openid-connect";
	private final static String RESOURCE_SERVER = "http://localhost:8081/resource-server";
	private final static String REDIRECT_URL = "http://localhost:8081/resource-server/swagger-ui/oauth2-redirect.html";
	private final static String CLIENT_ID = "newClient";
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
		String authorizeUrl = AUTH_SERVER + "/auth";

		Map<String, String> loginParams = new HashMap<String, String>();
		loginParams.put("grant_type", "implicit");
		loginParams.put("client_id", clientId);
		loginParams.put("response_type", "token");
		loginParams.put("redirect_uri", REDIRECT_URL);
		loginParams.put("scope", "oidc read write");

		// user login
		Response response = RestAssured.given().formParams(loginParams).get(authorizeUrl);
		String cookieValue = response.getCookie("AUTH_SESSION_ID");
	
		String authUrlWithCode = response.htmlPath().getString("'**'.find{node -> node.name()=='form'}*.@action");
		
		// get access token
		Map<String, String> tokenParams = new HashMap<String, String>();
		tokenParams.put("username", username);
		tokenParams.put("password", password);
		tokenParams.put("client_id", clientId);
		tokenParams.put("redirect_uri", REDIRECT_URL);
		response = RestAssured.given().cookie("AUTH_SESSION_ID", cookieValue).formParams(tokenParams)
				.post(authUrlWithCode);

		final String location = response.getHeader(HttpHeaders.LOCATION);

		Assertions.assertEquals(HttpStatus.FOUND.value(), response.getStatusCode());
		final String accessToken = location.split("#|=|&")[4];
		return accessToken;

	}

}

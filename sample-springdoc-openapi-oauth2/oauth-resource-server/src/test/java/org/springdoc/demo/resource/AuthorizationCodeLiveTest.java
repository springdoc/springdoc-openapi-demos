package org.springdoc.demo.resource;

import java.util.HashMap;
import java.util.Map;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

//Before running this live test make sure both authorization server and resource server are running   

public class AuthorizationCodeLiveTest {
    public final static String AUTH_SERVER = "http://localhost:8083/auth/realms/springdoc/protocol/openid-connect";
    public final static String RESOURCE_SERVER = "http://localhost:8081/resource-server"; 
    private final static String REDIRECT_URL = "http://localhost:8082/new-client/login/oauth2/code/custom";
	private final static String CLIENT_ID = "newClient";
	private final static String CLIENT_SECRET = "newClientSecret";

    @Test
    public void givenUser_whenUseFooClient_thenOkForFooResourceOnly() {
        final String accessToken = obtainAccessTokenWithAuthorizationCode("josh@test.com", "123");

        final Response fooResponse = RestAssured.given().header("Authorization", "Bearer " + accessToken).get(RESOURCE_SERVER + "/api/foos/1");
        Assertions.assertEquals(200, fooResponse.getStatusCode());
        Assertions.assertNotNull(fooResponse.jsonPath().get("name"));

    }

    private String obtainAccessTokenWithAuthorizationCode(String username, String password) {
    	

		String authorizeUrl = AUTH_SERVER + "/auth";
		String tokenUrl = AUTH_SERVER + "/token";

		Map<String, String> loginParams = new HashMap<String, String>();
		loginParams.put("client_id", CLIENT_ID);
		loginParams.put("response_type", "code");
		loginParams.put("redirect_uri", REDIRECT_URL);
		loginParams.put("scope", "read write");

		// user login
		Response response = RestAssured.given().formParams(loginParams).get(authorizeUrl);
		String cookieValue = response.getCookie("AUTH_SESSION_ID");
	
		String authUrlWithCode = response.htmlPath().getString("'**'.find{node -> node.name()=='form'}*.@action");
		
		// get code
		Map<String, String> codeParams = new HashMap<String, String>();
		codeParams.put("username", username);
		codeParams.put("password", password);
		response = RestAssured.given().cookie("AUTH_SESSION_ID", cookieValue).formParams(codeParams)
				.post(authUrlWithCode);

		final String location = response.getHeader(HttpHeaders.LOCATION);

		Assertions.assertEquals(HttpStatus.FOUND.value(), response.getStatusCode());
		final String code = location.split("#|=|&")[3];
		
		//get access token
		Map<String, String> tokenParams = new HashMap<String, String>();
		tokenParams.put("grant_type", "authorization_code");
		tokenParams.put("client_id", CLIENT_ID);
		tokenParams.put("client_secret", CLIENT_SECRET);
		tokenParams.put("redirect_uri", REDIRECT_URL);
		tokenParams.put("code", code);
		
		response = RestAssured.given().formParams(tokenParams)
				.post(tokenUrl);
		
		return response.jsonPath().getString("access_token");	
    }

}

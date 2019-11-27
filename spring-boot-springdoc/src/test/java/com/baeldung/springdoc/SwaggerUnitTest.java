package com.baeldung.springdoc;

import io.restassured.RestAssured;
import io.restassured.config.RedirectConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SwaggerUnitTest extends AbstractRestAssuredSetupClass {

    @Autowired
    protected MockMvc mockMvc;


    @Test
    public void shouldDisplaySwaggerUiPage() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/swagger-ui/index.html")).andExpect(status().isOk()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        assertTrue(contentAsString.contains("Swagger UI"));
    }
    /**
     * Test successful swagger-UI call
     **/
    @Test
    public void getSwaggerUIPage() {
        givenAnAdministratorIsAuthenticated()
                .contentType(ContentType.HTML)
                .get("/swagger-ui/index.html?url=/v3/api-docs")
                .then()
                .statusCode(HttpStatus.OK.value());
    }
}

abstract class AbstractRestAssuredSetupClass {

    @Value("${request.logging.enabled:true}")
    private boolean logRequests;
    @Value("${response.logging.enabled:true}")
    private boolean logResponses;
    @Value("${local.server.port}")
    private int port;

    @Before
    public void setUpRestAssured() {
        RestAssured.baseURI = "http://localhost:" + port;
        RestAssured.config = RestAssuredConfig.config().redirect(RedirectConfig.redirectConfig().followRedirects(false));
        if (logRequests) {
            RestAssured.filters(new RequestLoggingFilter());
        }
        if (logResponses) {
            RestAssured.filters(new ResponseLoggingFilter());
        }
    }

    protected RequestSpecification givenAnAdministratorIsAuthenticated() {
        return RestAssured.given().auth().preemptive().basic("user", "user");
    }
}

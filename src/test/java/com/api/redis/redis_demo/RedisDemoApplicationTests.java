package com.api.redis.redis_demo;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RedisDemoApplicationTests {

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setup(){
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
	}

	@Test
	void shouldCreateUser() {
		String requestBody = """
				{
				     "name": "Tony Stark",
				     "city": "London",
				     "country": "England"
				 }
				""";
			RestAssured.given()
				.contentType("application/json")
				.body(requestBody)
				.when()
				.post("/api/users")
				.then()
				.statusCode(200)
				.body("name", Matchers.equalTo("Tony Stark"))
				.body("city", Matchers.equalTo("London"))
				.body("country", Matchers.equalTo("England"));
	}

}

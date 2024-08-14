package com.api.redis.redis_demo;

import com.api.redis.redis_demo.dao.UserRepository;
import com.api.redis.redis_demo.model.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

import static org.hamcrest.Matchers.equalTo;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RedisDemoApplicationTests {

	@LocalServerPort
	private Integer port;

	@Autowired
	private UserRepository userRepository;

    @BeforeEach
	void setup() throws Exception {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = port;
		userRepository.save(new User("101","John","London","England"));
		userRepository.save(new User("102","Harry","Brrokyln","USA"));
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
				.body("name", equalTo("Tony Stark"))
				.body("city", equalTo("London"))
				.body("country", equalTo("England"));
	}

    @Test
    void shouldReturnUser(){
        RestAssured.given()
				.when()
				.get("/api/users/{id}",101)
				.then()
				.statusCode(200)
				.body("name", equalTo("John"))
				.body("city", equalTo("London"))
				.body("country", equalTo("England"));
    }

	@Test
	void shouldReturnAllUsers(){
		RestAssured.given()
				.when()
				.get("/api/users")
				.then()
				.statusCode(200)
				.body("users",Matchers.hasSize(2));
	}

    @Test
	void shouldUpdateUser(){
		String updatedUser = """
				{
				     "name": "Harvard Stark",
				     "city": "London",
				     "country": "England"
				 }
				""";

		RestAssured.given()
				.contentType("application/json")
				.body(updatedUser)
				.when()
				.put("/api/users/{id}", "101")
				.then()
				.statusCode(200)
				.contentType(ContentType.JSON)
				.body("name", equalTo("Harvard Stark"));
	}

    @Test
    void shouldDeleteUser(){
        RestAssured.given()
                .when()
                .delete("/api/users/{id}", "101")
                .then()
                .statusCode(200)
                .body(Matchers.equalTo("User deleted successfully"));
    }

}

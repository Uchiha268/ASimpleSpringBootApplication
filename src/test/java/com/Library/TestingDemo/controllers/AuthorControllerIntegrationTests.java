package com.Library.TestingDemo.controllers;

import com.Library.TestingDemo.TestEntityCreation;
import com.Library.TestingDemo.domain.entity.AuthorEntity;
import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerIntegrationTests {

    @Test
    public void testCompleteEndToEndAuthorCreationReturnsHTTPCreatedResponse(){
        AuthorEntity authorEntity = TestEntityCreation.createTestAuthor1();
        RestAssured.baseURI = "http://localhost:8080";

        RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(authorEntity)
                .when()
                .post("/authors")
                .then()
                .statusCode(201);

    }

    @Test
    public void testCompleteEndToEndAuthorReturnsSavedAuthor(){
        AuthorEntity authorEntity = TestEntityCreation.createTestAuthor2();
        RestAssured.baseURI = "http://localhost:8080";

        RestAssured
                .given()
                .header("Content-Type", "application/json")
                .body(authorEntity)
                .when()
                .post("/authors")
                .then()
                .body("id", isA(Number.class))
                .body("name", equalTo(authorEntity.getName()))
                .body("age", equalTo(authorEntity.getAge()));

    }
}

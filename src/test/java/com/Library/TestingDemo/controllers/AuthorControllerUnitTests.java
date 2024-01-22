package com.Library.TestingDemo.controllers;

import com.Library.TestingDemo.TestEntityCreation;
import com.Library.TestingDemo.domain.entity.AuthorEntity;
import com.Library.TestingDemo.repositories.AuthorRepository;
import io.restassured.RestAssured;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class AuthorControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    private final AuthorRepository authorRepository;
    @Autowired
    public AuthorControllerUnitTests(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @BeforeEach
    void setUp() {
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void testAuthorAPIReturnsHTTPCreatedResponse(){
        AuthorEntity authorEntity = TestEntityCreation.createTestAuthor3();

        RestAssuredMockMvc
                .given()
                .header("Content-Type", "application/json")
                .body(authorEntity)
                .when()
                .post("/authors")
                .then()
                .statusCode(201);

    }
    @Test
    public void testAuthorAPIReturnsAuthorSaved(){
        AuthorEntity authorEntity = TestEntityCreation.createTestAuthor3();

        RestAssuredMockMvc
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

    @Test
    public void testAuthorFindAllReturnsHttpOk(){
        AuthorEntity authorEntity = TestEntityCreation.createTestAuthor1();
        authorRepository.save(authorEntity);

        RestAssuredMockMvc
                .given()
                .header("Content-Type", "application/json")
                .when()
                .get("/authors")
                .then()
                .status(HttpStatus.OK);
    }

    @Test
    public void testAuthorFindAllReturnsAllAuthorsFromDb(){
        AuthorEntity authorEntity1 = TestEntityCreation.createTestAuthor1();
        AuthorEntity authorEntity2 = TestEntityCreation.createTestAuthor2();
        authorRepository.save(authorEntity1);
        authorRepository.save(authorEntity2);

        RestAssuredMockMvc
                .given()
                .header("Content-Type", "application/json")
                .when()
                .get("/authors")
                .then()
                .body("name", hasItems(authorEntity1.getName(), authorEntity2.getName()))
                .body("age", hasItems(authorEntity1.getAge(), authorEntity2.getAge()));
    }
}

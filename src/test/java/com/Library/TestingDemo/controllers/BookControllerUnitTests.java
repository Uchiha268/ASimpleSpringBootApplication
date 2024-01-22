package com.Library.TestingDemo.controllers;

import com.Library.TestingDemo.TestEntityCreation;
import com.Library.TestingDemo.domain.dto.AuthorDto;
import com.Library.TestingDemo.domain.dto.BookDto;
import com.Library.TestingDemo.domain.entity.AuthorEntity;
import com.Library.TestingDemo.domain.entity.BookEntity;
import com.Library.TestingDemo.repositories.BookRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
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

import java.awt.print.Book;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BookControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    private final BookRepository bookRepository;
    @Autowired
    public BookControllerUnitTests(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @BeforeEach
    public void setup(){
        RestAssuredMockMvc.mockMvc(mockMvc);
    }

    @Test
    public void testBookAPIReturnsHTTPCreatedResponse(){
        BookEntity bookEntity = TestEntityCreation.createTestBook1(null);

        RestAssuredMockMvc
                .given()
                .header("Content-Type", "application/json")
                .body(bookEntity)
                .when()
                .put("/books/1234")
                .then()
                .statusCode(201);

    }

    @Test
    public void testBookAPIReturnsBookSaved(){
        BookEntity bookEntity = TestEntityCreation.createTestBook1(null);

        RestAssuredMockMvc
                .given()
                .header("Content-Type", "application/json")
                .body(bookEntity)
                .when()
                .put("/books/" + bookEntity.getIsbn())
                .then()
                .body("isbn", equalTo(bookEntity.getIsbn()))
                .body("title", equalTo(bookEntity.getTitle()));

    }

    @Test
    public void testBookApiCreatesAuthorWithIt(){
        AuthorDto authorDto = AuthorDto.builder().name("Satyajit Ray").age(89).build();
        BookDto bookDto = TestEntityCreation.createTestBookDto(authorDto);

        RestAssuredMockMvc
                .given()
                .header("Content-Type", "application/json")
                .body(bookDto)
                .when()
                .put("/books/" + bookDto.getIsbn())
                .then()
                .body("author.name", equalTo(authorDto.getName()))
                .body("author.age", equalTo(authorDto.getAge()));
    }


}

package com.Library.TestingDemo;

import com.Library.TestingDemo.domain.dto.AuthorDto;
import com.Library.TestingDemo.domain.dto.BookDto;
import com.Library.TestingDemo.domain.entity.AuthorEntity;
import com.Library.TestingDemo.domain.entity.BookEntity;
import org.springframework.beans.factory.annotation.Autowired;

public class TestEntityCreation {
    public static AuthorEntity createTestAuthor1(){
        return AuthorEntity.builder()
                .name("Jane Austen")
                .age(42)
                .build();
    }

    public static AuthorEntity createTestAuthor2(){
        return AuthorEntity.builder()
                .name("Barry Allen")
                .age(39)
                .build();
    }

    public static AuthorEntity createTestAuthor3(){
        return AuthorEntity.builder()
                .name("David Gray")
                .age(73)
                .build();
    }

    public static AuthorDto createTestAuthor4(){
        return AuthorDto.builder()
                .name("Frank Hemsworth")
                .age(52)
                .build();
    }

    public static BookEntity createTestBook1(AuthorEntity authorEntity){
        return BookEntity.builder()
                .isbn("1234")
                .title("Way of the Woods")
                .authorEntity(authorEntity)
                .build();
    }
    public static BookEntity createTestBook2(AuthorEntity authorEntity){
        return BookEntity.builder()
                .isbn("5678")
                .title("Looking for Alaska")
                .authorEntity(authorEntity)
                .build();
    }
    public static BookEntity createTestBook3(AuthorEntity authorEntity){
        return BookEntity.builder()
                .isbn("9012")
                .title("Gazing Forever")
                .authorEntity(authorEntity)
                .build();
    }
    public static BookEntity createTestBook4(AuthorEntity authorEntity){
        return BookEntity.builder()
                .isbn("1324")
                .title("Excellence")
                .authorEntity(authorEntity)
                .build();
    }


    public static BookDto createTestBookDto(AuthorDto authorDto){
        return BookDto.builder()
                .isbn("7654")
                .title("Something Good")
                .author(authorDto)
                .build();
    }

    private static AuthorDto createTestAuthorDto(){
        return AuthorDto.builder()
                .name("Satyajit Ray")
                .age(89)
                .build();
    }
}

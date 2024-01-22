package com.Library.TestingDemo.controllers;

import com.Library.TestingDemo.domain.dto.BookDto;
import com.Library.TestingDemo.domain.entity.BookEntity;
import com.Library.TestingDemo.mapper.Mapper;
import com.Library.TestingDemo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class BookController {

    private final BookService bookService;

    private final Mapper<BookEntity, BookDto> mapper;

    @Autowired
    public BookController(BookService bookService, Mapper<BookEntity, BookDto> mapper) {
        this.bookService = bookService;
        this.mapper = mapper;
    }

    @PutMapping("/books/{isbn}")
    public ResponseEntity<BookDto> createBook(@PathVariable("isbn") String isbn, @RequestBody BookDto bookDto){
        BookEntity savedBook = bookService.createBook(isbn, mapper.mapFrom(bookDto));
        return new ResponseEntity<>(mapper.mapTo(savedBook), HttpStatus.CREATED);
    }
}

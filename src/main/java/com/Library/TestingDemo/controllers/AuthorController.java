package com.Library.TestingDemo.controllers;

import com.Library.TestingDemo.domain.dto.AuthorDto;
import com.Library.TestingDemo.domain.entity.AuthorEntity;
import com.Library.TestingDemo.mapper.Mapper;
import com.Library.TestingDemo.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AuthorController {
    private final AuthorService authorService;
    private final Mapper<AuthorEntity, AuthorDto> mapper;

    @Autowired
    public AuthorController(AuthorService authorService, Mapper<AuthorEntity, AuthorDto> mapper) {
        this.authorService = authorService;
        this.mapper = mapper;
    }

    @PostMapping("/authors")
    public ResponseEntity<AuthorDto> createAuthor(@RequestBody AuthorDto authorDto){
        AuthorEntity savedAuthor = authorService.createAuthor(mapper.mapFrom(authorDto));
        return new ResponseEntity<>(mapper.mapTo(savedAuthor), HttpStatus.CREATED);
    }

    @GetMapping("/authors")
    public ResponseEntity<List<AuthorDto>> listAuthors(){
        List<AuthorEntity> authors = authorService.findAll();
        return new ResponseEntity<>(authors.stream().map(mapper::mapTo).collect(Collectors.toList()), HttpStatus.OK);
    }
}

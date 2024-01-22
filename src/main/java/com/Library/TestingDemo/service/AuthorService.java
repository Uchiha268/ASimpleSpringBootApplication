package com.Library.TestingDemo.service;

import com.Library.TestingDemo.domain.entity.AuthorEntity;
import org.springframework.stereotype.Service;

import java.util.List;


public interface AuthorService {
    public AuthorEntity createAuthor(AuthorEntity authorEntity);
    public List<AuthorEntity> findAll();
}

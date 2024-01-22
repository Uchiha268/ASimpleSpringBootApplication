package com.Library.TestingDemo.service;

import com.Library.TestingDemo.domain.entity.BookEntity;

import java.awt.print.Book;
import java.util.List;

public interface BookService {
    public BookEntity createBook(String isbn, BookEntity bookEntity);
}

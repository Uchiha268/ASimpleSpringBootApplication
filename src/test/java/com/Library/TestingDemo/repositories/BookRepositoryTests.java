package com.Library.TestingDemo.repositories;

import com.Library.TestingDemo.TestEntityCreation;
import com.Library.TestingDemo.domain.entity.AuthorEntity;
import com.Library.TestingDemo.domain.entity.BookEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BookRepositoryTests {

    private final BookRepository bookTestRepository;
    private final AuthorRepository authorTestRepository;

    @Autowired
    public BookRepositoryTests(BookRepository bookTestRepository, AuthorRepository authorRepository) {
        this.bookTestRepository = bookTestRepository;
        this.authorTestRepository = authorRepository;
    }

    @Test
    public void testThatBookCanBeCreatedAndRecalled() {
        BookEntity bookEntity = TestEntityCreation.createTestBook1(TestEntityCreation.createTestAuthor1());
        bookTestRepository.save(bookEntity);
        Optional<BookEntity> result = bookTestRepository.findById(bookEntity.getIsbn());
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get()).isEqualTo(bookEntity);

    }

    @Test
    public void testThatBookCanBeCreatedAndRecalledWithoutCorrespondingAuthorCreated(){
        BookEntity bookEntity = TestEntityCreation.createTestBook2(TestEntityCreation.createTestAuthor2());
        bookTestRepository.save(bookEntity);
        Optional<BookEntity> result = bookTestRepository.findById(bookEntity.getIsbn());
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get()).isEqualTo(bookEntity);
        Optional<AuthorEntity> resultAuthor = authorTestRepository.findById(bookEntity.getAuthorEntity().getId());
        Assertions.assertThat(result).isPresent();

    }

    @Test
    public void testThatAllBooksCanBeCreatedAndRecalled(){
        BookEntity bookEntity1 = TestEntityCreation.createTestBook1(TestEntityCreation.createTestAuthor1());
        BookEntity bookEntity2 = TestEntityCreation.createTestBook2(TestEntityCreation.createTestAuthor2());
        BookEntity bookEntity3 = TestEntityCreation.createTestBook3(TestEntityCreation.createTestAuthor3());
        bookTestRepository.save(bookEntity1);
        bookTestRepository.save(bookEntity2);
        bookTestRepository.save(bookEntity3);
        Iterable<BookEntity> result = bookTestRepository.findAll();
        Assertions.assertThat(result)
                .hasSize(3)
                .containsExactly(bookEntity1, bookEntity2, bookEntity3);
    }
}

package com.Library.TestingDemo.repositories;

import com.Library.TestingDemo.TestEntityCreation;
import com.Library.TestingDemo.TestingDemoApplication;
import com.Library.TestingDemo.domain.entity.AuthorEntity;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import java.util.Optional;


@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthorRepositoryTests {

    private final AuthorRepository authorTestRepository;

    @Autowired
    public AuthorRepositoryTests(AuthorRepository authorRepository) {
        this.authorTestRepository = authorRepository;
    }

    @Test
    public void testThatAuthorCanBeCreatedAndRecalled(){
        AuthorEntity authorEntity = TestEntityCreation.createTestAuthor1();
        authorTestRepository.save(authorEntity);
        Optional<AuthorEntity> result = authorTestRepository.findById(authorEntity.getId());
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get()).isEqualTo(authorEntity);
    }

    @Test
    public void testThatAllAuthorsCanBeCreatedAndRecalled(){
        AuthorEntity authorEntity1 = TestEntityCreation.createTestAuthor1();
        AuthorEntity authorEntity2 = TestEntityCreation.createTestAuthor2();
        AuthorEntity authorEntity3 = TestEntityCreation.createTestAuthor3();
        authorTestRepository.save(authorEntity1);
        authorTestRepository.save(authorEntity2);
        authorTestRepository.save(authorEntity3);
        Iterable<AuthorEntity> result = authorTestRepository.findAll();
        Assertions.assertThat(result)
                .hasSize(3)
                .containsExactly(authorEntity1, authorEntity2, authorEntity3);
    }

    @Test
    public void testThatH2RefreshesAfterEveryTestMethod(){
        Iterable<AuthorEntity> result = authorTestRepository.findAll();
        Assertions.assertThat(result).hasSize(0);
    }

    @Test
    public void testThatAuthorCanBeUpdated(){
        AuthorEntity authorEntity = TestEntityCreation.createTestAuthor1();
        AuthorEntity authorEntityCopy = TestEntityCreation.createTestAuthor1();
        authorTestRepository.save(authorEntity);
        authorEntity.setName("Mark Zuckerburg");
        authorTestRepository.save(authorEntity);
        Optional<AuthorEntity> result = authorTestRepository.findById(authorEntity.getId());
        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get()).isEqualTo(authorEntity);
        Assertions.assertThat(result.get()).isNotEqualTo(authorEntityCopy);
    }

    @Test
    public void testThatAuthorCanBeDeleted(){
        AuthorEntity authorEntity = TestEntityCreation.createTestAuthor1();
        authorTestRepository.save(authorEntity);
        authorTestRepository.delete(authorEntity);
        Optional<AuthorEntity> result = authorTestRepository.findById(authorEntity.getId());
        Assertions.assertThat(result).isNotPresent();
    }
}

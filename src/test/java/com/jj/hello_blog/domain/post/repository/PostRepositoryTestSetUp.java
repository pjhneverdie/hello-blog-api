package com.jj.hello_blog.domain.post.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jj.hello_blog.domain.category.dto.Category;
import com.jj.hello_blog.domain.category.repository.CategoryRepository;
import com.jj.hello_blog.domain.category.repository.CategoryRepositoryTest;

@Transactional
@SpringBootTest
public class PostRepositoryTestSetUp {

    @Autowired
    private CategoryRepository categoryRepository;

    protected Category category;

    @BeforeEach
    @DisplayName("게시글을 작성할 카테고리 셋업")
    void setUp() {
        this.category = CategoryRepositoryTest.createCategory(null, "test", "test", null, null);
        categoryRepository.insertCategory(category);
    }

    @Test
    void contextLoads() {
    }

}

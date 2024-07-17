package com.jj.hello_blog.domain.category.repository.mybatis;

import com.jj.hello_blog.domain.category.entity.mybatis.Category;
import com.jj.hello_blog.domain.category.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MyBatisCategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void addCategory() {
        // Given
        Category category = new Category(null, "test", null);

        // When
        categoryRepository.addCategory(category);

        // Then
        assertNotNull(category.getId());
    }

}
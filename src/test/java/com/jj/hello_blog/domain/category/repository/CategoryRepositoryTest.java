package com.jj.hello_blog.domain.category.repository;

import com.jj.hello_blog.domain.category.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void addCategory() {
        // Given
        Category root = getRoot();

        // When
        categoryRepository.addCategory(root);

        // Then
        assertNotNull(root.getId());
    }

    @Test
    void getCategories() {
        // Given
        Category root = getRoot();

        categoryRepository.addCategory(root);

        List<Category> children = getChildren(root);

        for (Category child : children) {
            categoryRepository.addCategory(child);
        }

        // When
        List<Category> categories = categoryRepository.getCategories();

        // Then
        assertEquals(1 + children.size(), categories.size());
    }

    public static Category getRoot() {
        return new Category(null, "root", null);
    }

    private List<Category> getChildren(Category root) {
        List<Category> dummyCategories = new ArrayList<>();

        Category[] children = new Category[]{
                new Category(null, root.getName() + "-child1", root.getId()),
                new Category(null, root.getName() + "-child2", root.getId())
        };

        Collections.addAll(dummyCategories, children);

        return dummyCategories;
    }
}
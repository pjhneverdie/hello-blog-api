package com.jj.hello_blog.domain.category.repository;

import com.jj.hello_blog.domain.category.dto.Category;
import com.jj.hello_blog.domain.post.dto.Post;
import com.jj.hello_blog.domain.post.repository.PostRepository;
import com.jj.hello_blog.domain.post.repository.PostRepositoryTest;
import com.jj.hello_blog.domain.category.dto.CategoryResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("카테고리 추가 테스트")
    void saveCategory() {
        // Given
        Category category = getCategory();

        // When
        categoryRepository.saveCategory(category);

        // Then
        assertNotNull(category.getId());
    }

    @Test
    @DisplayName("카테고리 조회 테스트")
    void getCategories() {
        // Given
        Category category = getCategory();
        categoryRepository.saveCategory(category);

        Post postInCategory = PostRepositoryTest.getPost(category);
        postRepository.savePost(postInCategory);

        // When
        List<CategoryResponse> categories = categoryRepository.getCategories();

        // Then
        categories.forEach((categoryResponse) -> {
            assertEquals(categoryResponse.getId(), (int) category.getId());
            assertTrue(categoryResponse.getPosts().stream().allMatch(post -> postInCategory.getId().equals(post.getId())));
        });
    }

    @Test
    @DisplayName("카테고리 삭제 테스트")
    void deleteCategoryById() {
        // Given
        Category category = getCategory();
        categoryRepository.saveCategory(category);

        // When
        categoryRepository.deleteCategoryById(category.getId());

        // Then
        List<CategoryResponse> categories = categoryRepository.getCategories();
        assertTrue(categories.isEmpty());
    }

    /**
     * getCategory, 카테고리 데이터 생성 유틸
     *
     * @return 카테고리
     */
    public static Category getCategory() {
        return new Category(null, "test");
    }

}
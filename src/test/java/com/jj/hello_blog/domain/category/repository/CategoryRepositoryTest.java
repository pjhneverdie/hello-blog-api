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

    /**
     * 카테고리에 속한 글이 있어야 카테고리 조회 시 join 응답을 제대로 테스트할 수 있음
     */
    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("카테고리 추가 테스트")
    void addCategory() {
        // Given
        Category category = getCategory();

        // When
        categoryRepository.addCategory(category);

        // Then
        assertNotNull(category.getId());
    }

    @Test
    @DisplayName("카테고리 조회 테스트")
    void getCategories() {
        // Given
        Category category = getCategory();
        categoryRepository.addCategory(category);

        Post postInCategory = postRepository.save(PostRepositoryTest.getPost(category));

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
        categoryRepository.addCategory(category);

        // When
        categoryRepository.deleteCategoryById(category.getId());

        // Then
        List<CategoryResponse> categories = categoryRepository.getCategories();
        assertTrue(categories.isEmpty());
    }

    /**
     * 테스트용 카테고리 생성 유틸
     */
    public static Category getCategory() {
        return new Category(null, "test");
    }

}
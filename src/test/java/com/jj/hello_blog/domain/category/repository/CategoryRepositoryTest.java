package com.jj.hello_blog.domain.category.repository;

import java.util.List;
import java.time.LocalDateTime;

import com.jj.hello_blog.domain.category.dto.Category;
import com.jj.hello_blog.domain.category.dto.CategoryResponse;
import com.jj.hello_blog.domain.category.dto.CategoryUpdateQueryDto;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("카테고리 추가 테스트")
    void insertCategory() {
        // Given
        Category category = createCategory(null, "test", "test", null, null);

        // When
        categoryRepository.insertCategory(category);

        // Then
        assertNotNull(category.getId());
    }

    @Test
    @DisplayName("최상위 카테고리 조회 테스트")
    void selectCategoriesWhereParentIdIsNull() {
        // Given
        Category parent = createCategory(null, "parent", "test", null, null);
        categoryRepository.insertCategory(parent);

        Category child = createCategory(null, "child", "test", parent.getId(), null);
        categoryRepository.insertCategory(child);

        // When
        List<CategoryResponse> categoryResponses = categoryRepository.selectCategoriesWhereParentIdIsNull();

        // Then
        assertEquals(categoryResponses.get(0).getId(), parent.getId());
        assertEquals(categoryResponses.get(0).getName(), parent.getName());
        assertEquals(categoryResponses.get(0).getThumbUrl(), parent.getThumbUrl());
        assertEquals(categoryResponses.get(0).getCreatedAt(), parent.getCreatedAt());
        assertEquals(categoryResponses.get(0).getPostCount(), 0);
    }

    @Test
    @DisplayName("하위 카테고리 조회 테스트")
    void selectCategoriesByParentId() {
        // Given
        Category parent = createCategory(null, "parent", "test", null, null);
        categoryRepository.insertCategory(parent);

        Category child = createCategory(null, "child", "test", parent.getId(), null);
        categoryRepository.insertCategory(child);

        CategoryResponse[] expectedResponses = {
                new CategoryResponse(parent.getId(), parent.getName(), parent.getThumbUrl(), parent.getParentId(), parent.getCreatedAt(), 0),
                new CategoryResponse(child.getId(), child.getName(), child.getThumbUrl(), child.getParentId(), child.getCreatedAt(), 0),
        };

        // When
        List<CategoryResponse> categoryResponses = categoryRepository.selectCategoriesByParentId(parent.getId());

        // Then
        for (int i = 0; i < categoryResponses.size(); i++) {
            assertEquals(categoryResponses.get(i).getId(), expectedResponses[i].getId());
            assertEquals(categoryResponses.get(i).getName(), expectedResponses[i].getName());
            assertEquals(categoryResponses.get(i).getThumbUrl(), expectedResponses[i].getThumbUrl());
            assertEquals(categoryResponses.get(i).getParentId(), expectedResponses[i].getParentId());
            assertEquals(categoryResponses.get(i).getCreatedAt(), expectedResponses[i].getCreatedAt());
        }
    }

    @Test
    @DisplayName("카테고리 수정 테스트")
    void updateCategoryById() {
        // Given
        Category category = createCategory(null, "test", "test", null, null);
        categoryRepository.insertCategory(category);

        CategoryUpdateQueryDto categoryUpdateQueryDto = new CategoryUpdateQueryDto(category.getId(), category.getName() + "update", category.getThumbUrl(), category.getParentId());

        // When
        categoryRepository.updateCategoryById(categoryUpdateQueryDto);

        // Then
        List<CategoryResponse> categories = categoryRepository.selectCategoriesWhereParentIdIsNull();

        categories.forEach((categoryResponse) -> {
            assertEquals(categoryResponse.getId(), categoryUpdateQueryDto.getId());
            assertEquals(categoryResponse.getId(), (int) category.getId());
        });
    }

    @Test
    @DisplayName("카테고리 삭제 테스트")
    void deleteCategoryById() {
        // Given
        Category category = createCategory(null, "test", "test", null, null);
        categoryRepository.insertCategory(category);

        // When
        categoryRepository.deleteCategoryById(category.getId());

        // Then
        List<CategoryResponse> categories = categoryRepository.selectCategoriesWhereParentIdIsNull();
        assertTrue(categories.isEmpty());
    }

    /**
     * createCategory, 카테고리 데이터 생성 유틸
     */
    public static Category createCategory(Integer id, String name, String thumbUrl, Integer parentId, LocalDateTime createAt) {
        return new Category(id, name, thumbUrl, parentId, createAt);
    }

}
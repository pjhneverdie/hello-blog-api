package com.jj.hello_blog.domain.category.repository;

import java.util.List;

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
        Category category = createCategory(null, "thumbUrl", "test", null);

        // When
        categoryRepository.insertCategory(category);

        // Then
        assertNotNull(category.getId());
    }

    @Test
    @DisplayName("하위 카테고리 조회 테스트")
    void selectCategoriesByParentId() {
        // Given
        Category parent = createCategory(null, "thumbUrl", "parent", null);
        categoryRepository.insertCategory(parent);

        Category child = createCategory(null, "thumbUrl", "child", parent.getId());
        categoryRepository.insertCategory(child);

        // When
        List<CategoryResponse> categoryResponses = categoryRepository.selectCategoriesByParentId(parent.getId());

        // Then
        categoryResponses.get(0).getId().equals(parent.getId());
        categoryResponses.get(1).getId().equals(child.getId());
    }

    @Test
    @DisplayName("최상위 카테고리 조회 테스트")
    void selectCategoriesWhereParentIdIsNull() {
        // Given
        Category parent = createCategory(null, "thumbUrl", "parent", null);
        categoryRepository.insertCategory(parent);

        // When
        List<CategoryResponse> categoryResponses = categoryRepository.selectCategoriesWhereParentIdIsNull();

        // Then
        categoryResponses.get(0).getId().equals(parent.getId());
    }

    @Test
    @DisplayName("카테고리 수정 테스트")
    void updateCategoryById() {
        // Given
        Category category = createCategory(null, "thumbUrl", "test", null);
        categoryRepository.insertCategory(category);

        CategoryUpdateQueryDto categoryUpdateQueryDto = new CategoryUpdateQueryDto(category.getId(), category.getThumbUrl(), category.getName() + "update", category.getParentId());

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
        Category category = createCategory(null, "thumbUrl", "parent", null);
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
    public static Category createCategory(Integer id, String thumbUrl, String name, Integer parentId) {
        return new Category(id, thumbUrl, name, parentId);
    }

}
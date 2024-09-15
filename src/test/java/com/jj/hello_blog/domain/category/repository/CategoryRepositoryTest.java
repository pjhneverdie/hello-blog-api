package com.jj.hello_blog.domain.category.repository;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jj.hello_blog.domain.category.dto.Category;
import com.jj.hello_blog.domain.category.dto.CategoryResponse;
import com.jj.hello_blog.domain.category.dto.CategoryUpdateQueryDto;

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
        Category category = createCategory(null, "test", "thumbUrl", null, null);

        // When
        categoryRepository.insertCategory(category);

        // Then
        assertNotNull(category.getId());
    }

    @Test
    @DisplayName("카테고리 id로 조회 테스트")
    void selectCategoryById() {
        // Given
        Category category = createCategory(null, "test", "thumbUrl", null, null);
        categoryRepository.insertCategory(category);

        // When
        Optional<Category> selectedCategory = categoryRepository.selectCategoryById(category.getId());

        // Then
        assertTrue(selectedCategory.isPresent());
        assertEquals(category.getId(), selectedCategory.get().getId());
        assertEquals(category.getName(), selectedCategory.get().getName());
        assertEquals(category.getThumbUrl(), selectedCategory.get().getThumbUrl());
        assertEquals(category.getParentId(), selectedCategory.get().getParentId());
    }

    @Test
    @DisplayName("카테고리 이름으로 조회 테스트")
    void selectCategoriesByName() {
        // Given
        String name = "test";

        List<Category> byName = new ArrayList<>();

        Category category1 = createCategory(1, name, "thumbUrl", null, null);
        Category category2 = createCategory(2, name, "thumbUrl", null, null);

        byName.add(category1);
        byName.add(category2);

        categoryRepository.insertCategory(category1);
        categoryRepository.insertCategory(category2);

        // When
        List<Category> categories = categoryRepository.selectCategoriesByName(name);

        // Then
        for (int i = 0; i < categories.size(); i++) {
            assertEquals(byName.get(i).getId(), categories.get(i).getId());
        }

    }

    @Test
    @DisplayName("카테고리의 모든 자식 카테고리 조회 테스트")
    void selectAllSubCategoriesById() {
        // Given
        Category parent = createCategory(null, "test", "test", null, null);
        categoryRepository.insertCategory(parent);

        Category child = createCategory(null, "child", "test", parent.getId(), null);
        categoryRepository.insertCategory(child);

        Category child_child1 = createCategory(null, "child_child1", "test", child.getId(), null);
        categoryRepository.insertCategory(child_child1);

        Category child_child2 = createCategory(null, "child_child2", "test", child.getId(), null);
        categoryRepository.insertCategory(child_child2);

        Category[] expectedResponse = {child, child_child1, child_child2};

        // When
        List<Category> children = categoryRepository.selectAllSubCategoriesById(child.getId());

        // Then
        assertEquals(children.size(), expectedResponse.length);

        for (int i = 0; i < children.size(); i++) {
            assertEquals(children.get(i).getId(), expectedResponse[i].getId());
            assertEquals(children.get(i).getName(), expectedResponse[i].getName());
            assertEquals(children.get(i).getThumbUrl(), expectedResponse[i].getThumbUrl());
        }
    }

    @Test
    @DisplayName("카테고리랑 그 카테고리에 게시된 글의 개수 조회 테스트")
    void selectCategoryAndPostCountJoinPostById() {
        // Given
        Category category = createCategory(null, "test", "test", null, null);
        categoryRepository.insertCategory(category);

        CategoryResponse expectedResponse = new CategoryResponse(category.getId(), category.getName(), category.getThumbUrl(), category.getParentId(), category.getCreatedAt(), 0);

        // When
        CategoryResponse categoryResponse = categoryRepository.selectCategoryAndPostCountJoinPostById(category.getId());

        // Then
        assertEquals(categoryResponse.getId(), expectedResponse.getId());
        assertEquals(categoryResponse.getName(), expectedResponse.getName());
        assertEquals(categoryResponse.getThumbUrl(), expectedResponse.getThumbUrl());
        assertEquals(categoryResponse.getParentId(), expectedResponse.getParentId());
        assertEquals(categoryResponse.getPostCount(), expectedResponse.getPostCount());
    }

    @Test
    @DisplayName("최상위 카테고리 조회 테스트")
    void selectCategoriesWhereParentIdIsNull() {
        // Given
        Category parent1 = createCategory(null, "parent1", "test", null, null);
        categoryRepository.insertCategory(parent1);

        Category parent2 = createCategory(null, "parent2", "test", null, null);
        categoryRepository.insertCategory(parent2);

        CategoryResponse[] expectedResponse = {
                new CategoryResponse(parent1.getId(), parent1.getName(), parent1.getThumbUrl(), parent1.getParentId(), parent1.getCreatedAt(), 0),
                new CategoryResponse(parent2.getId(), parent2.getName(), parent2.getThumbUrl(), parent2.getParentId(), parent2.getCreatedAt(), 0)
        };

        // When
        List<CategoryResponse> categoryResponses = categoryRepository.selectCategoriesWhereParentIdIsNull();

        // Then

        for (int i = 0; i < categoryResponses.size(); i++) {
            assertEquals(categoryResponses.get(i).getId(), expectedResponse[i].getId());
            assertEquals(categoryResponses.get(i).getName(), expectedResponse[i].getName());
            assertEquals(categoryResponses.get(i).getThumbUrl(), expectedResponse[i].getThumbUrl());
            assertEquals(categoryResponses.get(i).getPostCount(), expectedResponse[i].getPostCount());
        }
    }

    @Test
    @DisplayName("하위 카테고리 조회 테스트")
    void selectCategoriesByParentId() {
        // Given
        Category parent = createCategory(null, "parent", "test", null, null);
        categoryRepository.insertCategory(parent);

        Category child = createCategory(null, "child", "test", parent.getId(), null);
        categoryRepository.insertCategory(child);

        CategoryResponse[] expectedResponse = {
                new CategoryResponse(parent.getId(), parent.getName(), parent.getThumbUrl(), parent.getParentId(), parent.getCreatedAt(), 0),
                new CategoryResponse(child.getId(), child.getName(), child.getThumbUrl(), child.getParentId(), child.getCreatedAt(), 0),
        };

        // When
        List<CategoryResponse> categoryResponses = categoryRepository.selectCategoriesByParentId(parent.getId());

        // Then
        for (int i = 0; i < categoryResponses.size(); i++) {
            assertEquals(categoryResponses.get(i).getId(), expectedResponse[i].getId());
            assertEquals(categoryResponses.get(i).getName(), expectedResponse[i].getName());
            assertEquals(categoryResponses.get(i).getThumbUrl(), expectedResponse[i].getThumbUrl());
            assertEquals(categoryResponses.get(i).getParentId(), expectedResponse[i].getParentId());
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
        Optional<Category> updated = categoryRepository.selectCategoryById(category.getId());

        assertTrue(updated.isPresent());
        assertEquals(category.getId(), updated.get().getId());
        // 이름만 수정했으니
        assertNotEquals(category.getName(), updated.get().getName());
        assertEquals(category.getThumbUrl(), updated.get().getThumbUrl());
        assertEquals(category.getParentId(), updated.get().getParentId());
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
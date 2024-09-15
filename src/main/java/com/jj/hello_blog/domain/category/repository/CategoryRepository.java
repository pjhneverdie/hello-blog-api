package com.jj.hello_blog.domain.category.repository;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import com.jj.hello_blog.domain.category.dto.*;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final CategoryMapper categoryMapper;

    public void insertCategory(Category category) {
        categoryMapper.insertCategory(category);
    }

    public Optional<Category> selectCategoryById(int id) {
        return Optional.ofNullable(categoryMapper.selectCategoryById(id));
    }

    public List<Category> selectCategoriesByName(String name) {
        return categoryMapper.selectCategoriesByName(name);
    }

    public List<Category> selectAllSubCategoriesById(int id) {
        return categoryMapper.selectAllSubCategoriesById(id);
    }

    public CategoryResponse selectCategoryAndPostCountJoinPostById(int id) {
        return categoryMapper.selectCategoryAndPostCountJoinPostById(id);
    }

    public List<CategoryResponse> selectCategoriesWhereParentIdIsNull() {
        return categoryMapper.selectCategoriesWhereParentIdIsNull();
    }

    public List<CategoryResponse> selectCategoriesByParentId(int parentId) {
        return categoryMapper.selectCategoriesByParentId(parentId);
    }

    public void updateCategoryById(CategoryUpdateQueryDto categoryUpdateQueryDto) {
        categoryMapper.updateCategoryById(categoryUpdateQueryDto);
    }

    public void deleteCategoryById(int id) {
        categoryMapper.deleteCategoryById(id);
    }

}

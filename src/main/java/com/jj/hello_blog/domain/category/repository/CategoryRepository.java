package com.jj.hello_blog.domain.category.repository;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import com.jj.hello_blog.domain.category.dto.Category;
import com.jj.hello_blog.domain.category.dto.CategoryResponse;
import com.jj.hello_blog.domain.category.dto.CategoryUpdateQueryDto;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final CategoryMapper categoryMapper;

    public void insertCategory(Category category) {
        categoryMapper.insertCategory(category);
    }

    public void updateCategoryById(CategoryUpdateQueryDto categoryUpdateQueryDto) {
        categoryMapper.updateCategoryById(categoryUpdateQueryDto);
    }

    public void deleteCategoryById(int id) {
        categoryMapper.deleteCategoryById(id);
    }

    public List<CategoryResponse> selectCategoriesWhereParentIdIsNull() {
        return categoryMapper.selectCategoriesWhereParentIdIsNull();
    }

    public List<CategoryResponse> selectCategoriesByParentId(int parentId) {
        return categoryMapper.selectCategoriesByParentId(parentId);
    }

}

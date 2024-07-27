package com.jj.hello_blog.domain.category.repository;

import com.jj.hello_blog.domain.category.dto.Category;
import com.jj.hello_blog.domain.category.dto.CategoryResponse;
import com.jj.hello_blog.domain.category.dto.CategoryUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final CategoryMapper categoryMapper;

    public void saveCategory(Category category) {
        categoryMapper.saveCategory(category);
    }

    public void updateCategory(CategoryUpdateDto categoryUpdateDto) {
        categoryMapper.updateCategory(categoryUpdateDto);
    }

    public void deleteCategory(int id) {
        categoryMapper.deleteCategory(id);
    }

    public List<CategoryResponse> findAllCategories() {
        return categoryMapper.findAllCategories();
    }

}

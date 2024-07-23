package com.jj.hello_blog.domain.category.repository;

import com.jj.hello_blog.domain.category.dto.Category;
import com.jj.hello_blog.domain.category.dto.CategoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final CategoryMapper categoryMapper;

    public void addCategory(Category category) {
        categoryMapper.addCategory(category);
    }

    public List<CategoryResponse> getCategories() {
        return categoryMapper.getCategories();
    }

    public void deleteCategoryById(int id) {
        categoryMapper.deleteCategoryById(id);
    }
}

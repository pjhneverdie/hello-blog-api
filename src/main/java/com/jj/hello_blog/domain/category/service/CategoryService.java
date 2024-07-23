package com.jj.hello_blog.domain.category.service;

import com.jj.hello_blog.domain.category.dto.Category;
import com.jj.hello_blog.domain.category.repository.CategoryRepository;
import com.jj.hello_blog.domain.category.dto.CategoryResponse;
import com.jj.hello_blog.web.category.form.CategorySaveForm;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponse addCategory(CategorySaveForm categorySaveForm) {
        Category category = new Category(null, categorySaveForm.getName());

        categoryRepository.addCategory(category);

        return new CategoryResponse(category.getId(), category.getName(), new ArrayList<>());
    }

    public List<CategoryResponse> getCategories() {
        return categoryRepository.getCategories();
    }

}

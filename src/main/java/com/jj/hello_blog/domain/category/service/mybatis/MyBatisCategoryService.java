package com.jj.hello_blog.domain.category.service.mybatis;

import com.jj.hello_blog.domain.category.entity.mybatis.Category;
import com.jj.hello_blog.domain.category.repository.CategoryRepository;
import com.jj.hello_blog.domain.category.service.CategoryService;
import com.jj.hello_blog.web.category.dto.CategoryResponse;
import com.jj.hello_blog.web.category.form.CategoryAddForm;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyBatisCategoryService implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryResponse addCategory(CategoryAddForm categoryAddForm) {
        Category category = new Category(null, categoryAddForm.getName(), categoryAddForm.getParentId());

        categoryRepository.addCategory(category);

        return new CategoryResponse(category.getId(), category.getName());
    }
}

package com.jj.hello_blog.domain.category.service;

import com.jj.hello_blog.domain.category.dto.Category;
import com.jj.hello_blog.domain.category.dto.CategorySaveDto;
import com.jj.hello_blog.domain.category.repository.CategoryRepository;
import com.jj.hello_blog.domain.category.dto.CategoryResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * saveCategory, 카테고리 추가
     *
     * @return CategoryResponse
     */
    public CategoryResponse saveCategory(CategorySaveDto categorySaveDto) {
        Category category = new Category(null, categorySaveDto.getName());

        categoryRepository.saveCategory(category);

        return new CategoryResponse(category.getId(), category.getName(), new ArrayList<>());
    }

    /**
     * getCategories, 카테고리 조회
     *
     * @return List<CategoryResponse>
     */
    public List<CategoryResponse> getCategories() {
        return categoryRepository.getCategories();
    }

}

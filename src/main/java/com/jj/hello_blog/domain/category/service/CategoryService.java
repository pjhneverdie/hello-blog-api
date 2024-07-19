package com.jj.hello_blog.domain.category.service;

import com.jj.hello_blog.domain.category.entity.Category;
import com.jj.hello_blog.domain.category.repository.CategoryRepository;
import com.jj.hello_blog.web.category.dto.CategoryResponse;
import com.jj.hello_blog.web.category.form.CategoryAddForm;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryResponse addCategory(CategoryAddForm categoryAddForm) {
        Category category = new Category(null, categoryAddForm.getName(), categoryAddForm.getParentId());

        categoryRepository.addCategory(category);

        return new CategoryResponse(category.getId(), category.getName());
    }

    public List<CategoryResponse> getCategories() {
        List<CategoryResponse> rootCategories = new ArrayList<>();
        Map<Integer, CategoryResponse> categoryResponseMap = new HashMap<>();

        List<Category> categories = categoryRepository.getCategories();

        // Category -> CategoryResponse로 변환
        for (Category category : categories) {
            CategoryResponse response = new CategoryResponse(category.getId(), category.getName());
            categoryResponseMap.put(category.getId(), response);
        }

        // 카테고리 계층 구조 생성
        for (Category category : categories) {
            // 최상위 카테고리를 rootCategories에 추가
            if (category.getParentId() == null) {
                rootCategories.add(categoryResponseMap.get(category.getId()));
            } else {
                // 하위 카테고리를 상위 카테고리의 children에 추가
                categoryResponseMap.get(category.getParentId())
                        .getChildren()
                        .add(categoryResponseMap.get(category.getId()));
            }
        }

        return rootCategories;
    }
}

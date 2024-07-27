package com.jj.hello_blog.domain.category.service;

import com.jj.hello_blog.domain.category.dto.Category;
import com.jj.hello_blog.domain.category.dto.CategorySaveDto;
import com.jj.hello_blog.domain.category.dto.CategoryUpdateDto;
import com.jj.hello_blog.domain.category.exception.CategoryExceptionCode;
import com.jj.hello_blog.domain.category.repository.CategoryRepository;
import com.jj.hello_blog.domain.category.dto.CategoryResponse;

import com.jj.hello_blog.domain.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    /**
     * saveCategory, 카테고리 추가
     */
    public CategoryResponse saveCategory(CategorySaveDto categorySaveDto) {
        try {
            Category category = new Category(null, categorySaveDto.getName());

            categoryRepository.saveCategory(category);

            return new CategoryResponse(category.getId(), category.getName(), new ArrayList<>());
        } catch (DuplicateKeyException e) {
            // 카테고리 이름 중복 시
            throw new CustomException(CategoryExceptionCode.DUPLICATED_CATEGORY);
        }
    }

    /**
     * updateCategory, 카테고리 수정
     */
    public boolean updateCategory(CategoryUpdateDto categoryUpdateDto) {
        categoryRepository.updateCategory(categoryUpdateDto);
        return true;
    }

    /**
     * getCategories, 카테고리 조회
     */
    public List<CategoryResponse> findAllCategories() {
        return categoryRepository.findAllCategories();
    }

}

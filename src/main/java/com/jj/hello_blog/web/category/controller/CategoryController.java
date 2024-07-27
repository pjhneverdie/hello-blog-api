package com.jj.hello_blog.web.category.controller;

import com.jj.hello_blog.domain.category.dto.CategorySaveDto;
import com.jj.hello_blog.domain.category.dto.CategoryUpdateDto;
import com.jj.hello_blog.domain.category.service.CategoryService;
import com.jj.hello_blog.web.common.response.ApiResponse;
import com.jj.hello_blog.web.category.form.CategoryUpdateForm;
import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;

import com.jj.hello_blog.domain.category.dto.CategoryResponse;
import com.jj.hello_blog.web.category.form.CategorySaveForm;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/category")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    ResponseEntity<ApiResponse<CategoryResponse>> saveCategory(@Valid @RequestBody CategorySaveForm categorySaveForm) {
        return ResponseEntity.ok(new ApiResponse<>(categoryService.saveCategory(new CategorySaveDto(categorySaveForm.getName()))));
    }

    @PatchMapping
    ResponseEntity<ApiResponse<Boolean>> updateCategory(@Valid @RequestBody CategoryUpdateForm categoryUpdateForm) {
        return ResponseEntity.ok(new ApiResponse<>(categoryService.updateCategory(new CategoryUpdateDto(categoryUpdateForm.getId(), categoryUpdateForm.getName()))));
    }

    @GetMapping
    ResponseEntity<ApiResponse<List<CategoryResponse>>> findAllCategories() {
        return ResponseEntity.ok(new ApiResponse<>(categoryService.findAllCategories()));
    }

}

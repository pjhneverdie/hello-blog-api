package com.jj.hello_blog.web.category.controller;

import com.jj.hello_blog.domain.category.dto.CategoryUpdateResponse;
import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.jj.hello_blog.web.common.response.ApiResponse;
import com.jj.hello_blog.web.common.validation.FileTypeConstraint;

import com.jj.hello_blog.web.category.form.CategoryAddForm;
import com.jj.hello_blog.web.category.form.CategoryUpdateForm;

import com.jj.hello_blog.domain.category.dto.CategoryAddDto;
import com.jj.hello_blog.domain.category.dto.CategoryResponse;
import com.jj.hello_blog.domain.category.dto.CategoryUpdateDto;
import com.jj.hello_blog.domain.category.service.CategoryService;

import org.springframework.web.bind.annotation.*;

@RequestMapping("/admin/category")
@RestController
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<ApiResponse<CategoryResponse>> addCategory(
            @Valid @FileTypeConstraint @RequestPart(required = false) MultipartFile thumbImageFile,
            @Valid @RequestPart CategoryAddForm categoryAddForm
    ) {
        CategoryAddDto categoryAddDto = new CategoryAddDto(categoryAddForm.getName(), thumbImageFile, categoryAddForm.getParentId());

        return ResponseEntity.ok(new ApiResponse<>(categoryService.addCategory(categoryAddDto)));
    }

    @PatchMapping
    ResponseEntity<ApiResponse<CategoryUpdateResponse>> updateCategory(
            @Valid @FileTypeConstraint @RequestPart(required = false) MultipartFile thumbImageFile,
            @Valid @RequestPart CategoryUpdateForm categoryUpdateForm
    ) {
        CategoryUpdateDto categoryUpdateDto = new CategoryUpdateDto(categoryUpdateForm.getId(), categoryUpdateForm.getName(), categoryUpdateForm.getThumbUrl(), thumbImageFile, categoryUpdateForm.getParentId());

        return ResponseEntity.ok(new ApiResponse<>(categoryService.updateCategory(categoryUpdateDto)));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse<Boolean>> deleteCategory(@PathVariable @NotNull Integer id) {
        return ResponseEntity.ok(new ApiResponse<>(categoryService.deleteCategory(id)));
    }

}
package com.jj.hello_blog.web.category.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.validation.annotation.Validated;

import com.jj.hello_blog.web.common.response.ApiResponse;
import com.jj.hello_blog.web.common.validation.FileTypeConstraint;

import com.jj.hello_blog.web.category.form.CategoryAddForm;
import com.jj.hello_blog.web.category.form.CategoryUpdateForm;

import com.jj.hello_blog.domain.category.dto.CategoryAddDto;
import com.jj.hello_blog.domain.category.dto.CategoryResponse;
import com.jj.hello_blog.domain.category.dto.CategoryUpdateDto;
import com.jj.hello_blog.domain.category.service.CategoryService;

import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("/admin/category")
@RestController
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<ApiResponse<CategoryResponse>> addCategory(
            @Valid @FileTypeConstraint @RequestPart(required = false) MultipartFile thumbImageFile,
            @Valid  @RequestPart CategoryAddForm categoryAddForm
    ) {
        CategoryAddDto categoryAddDto = new CategoryAddDto(categoryAddForm.getName(), thumbImageFile, categoryAddForm.getParentId());

        int savedId = categoryService.addCategory(categoryAddDto);

        return ResponseEntity.ok(new ApiResponse<>(categoryService.getCategoryAndPostCount(savedId)));
    }

    @PatchMapping
    ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(
            @Valid @FileTypeConstraint @RequestPart(required = false) MultipartFile thumbImageFile,
            @Valid @RequestPart CategoryUpdateForm categoryUpdateForm
    ) {
        CategoryUpdateDto categoryUpdateDto = new CategoryUpdateDto(categoryUpdateForm.getId(), categoryUpdateForm.getName(), categoryUpdateForm.getThumbUrl(), thumbImageFile, categoryUpdateForm.getParentId());

        categoryService.updateCategory(categoryUpdateDto);

        return ResponseEntity.ok(new ApiResponse<>(categoryService.getCategoryAndPostCount(categoryUpdateForm.getId())));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse<Void>> deleteCategory(@NotNull @PathVariable Integer id) {
        categoryService.deleteCategory(id);

        return ResponseEntity.ok(new ApiResponse<>(null));
    }

}
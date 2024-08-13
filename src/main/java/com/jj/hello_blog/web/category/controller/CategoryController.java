package com.jj.hello_blog.web.category.controller;

import java.util.List;

import com.jj.hello_blog.domain.category.dto.CategoryHierarchyResponse;
import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.jj.hello_blog.web.common.response.ApiResponse;
import com.jj.hello_blog.web.post.validation.FileTypeConstraint;

import com.jj.hello_blog.web.category.form.CategoryAddForm;
import com.jj.hello_blog.web.category.form.CategoryUpdateForm;

import com.jj.hello_blog.domain.category.dto.CategoryAddDto;
import com.jj.hello_blog.domain.category.dto.CategoryResponse;
import com.jj.hello_blog.domain.category.dto.CategoryUpdateDto;
import com.jj.hello_blog.domain.category.service.CategoryService;

import org.springframework.web.bind.annotation.*;


@RequestMapping("/category")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    ResponseEntity<ApiResponse<CategoryResponse>> addCategory(
            @Valid @FileTypeConstraint @RequestPart(required = false) MultipartFile thumbImageFile,
            @Valid @RequestPart CategoryAddForm categoryAddForm
    ) {
        CategoryAddDto categoryAddDto = new CategoryAddDto(thumbImageFile, categoryAddForm.getName(), categoryAddForm.getParentId());

        return ResponseEntity.ok(new ApiResponse<>(categoryService.addCategory(categoryAddDto)));
    }

    @GetMapping("/hierarchy")
    ResponseEntity<ApiResponse<List<CategoryHierarchyResponse>>> findCategoryHierarchy() {
        List<CategoryHierarchyResponse> categoryHierarchy = categoryService.findCategoryHierarchy();

        return ResponseEntity.ok(new ApiResponse<>(categoryHierarchy));
    }

    @GetMapping()
    ResponseEntity<ApiResponse<List<CategoryResponse>>> findCategories() {
        List<CategoryResponse> categories = categoryService.findCategories();

        return ResponseEntity.ok(new ApiResponse<>(categories));
    }

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<List<CategoryResponse>>> findCategories(@PathVariable Integer id) {
        List<CategoryResponse> categories = categoryService.findCategories(id);

        return ResponseEntity.ok(new ApiResponse<>(categories));
    }

    @PatchMapping
    ResponseEntity<ApiResponse<Boolean>> updateCategory(
            @Valid @FileTypeConstraint @RequestPart(required = false) MultipartFile thumbImageFile,
            @Valid @RequestBody CategoryUpdateForm categoryUpdateForm
    ) {
        CategoryUpdateDto categoryUpdateDto = new CategoryUpdateDto(categoryUpdateForm.getId(), thumbImageFile, categoryUpdateForm.getName(), categoryUpdateForm.getParentId());

        return ResponseEntity.ok(new ApiResponse<>(categoryService.updateCategory(categoryUpdateDto)));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ApiResponse<Boolean>> deleteCategory(@PathVariable @NotNull Integer id) {
        return ResponseEntity.ok(new ApiResponse<>(categoryService.deleteCategory(id)));
    }

}

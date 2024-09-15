package com.jj.hello_blog.web.category.controller;

import java.util.List;

import jakarta.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

import com.jj.hello_blog.web.common.response.ApiResponse;

import com.jj.hello_blog.domain.category.dto.CategoryResponse;
import com.jj.hello_blog.domain.category.service.CategoryService;

import org.springframework.web.bind.annotation.*;

@Validated
@RequestMapping("/category")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    ResponseEntity<ApiResponse<List<CategoryResponse>>> getRootCategories() {
        List<CategoryResponse> categories = categoryService.getRootCategories();

        return ResponseEntity.ok(new ApiResponse<>(categories));
    }

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<List<CategoryResponse>>> getSubCategories(@NotNull @PathVariable Integer id) {
        List<CategoryResponse> categories = categoryService.getSubCategories(id);

        return ResponseEntity.ok(new ApiResponse<>(categories));
    }

}

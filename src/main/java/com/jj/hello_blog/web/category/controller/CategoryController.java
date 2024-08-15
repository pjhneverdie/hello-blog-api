package com.jj.hello_blog.web.category.controller;

import java.util.List;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;

import com.jj.hello_blog.web.common.response.ApiResponse;

import com.jj.hello_blog.domain.category.dto.CategoryResponse;
import com.jj.hello_blog.domain.category.service.CategoryService;

import org.springframework.web.bind.annotation.*;


@RequestMapping("/category")
@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping()
    ResponseEntity<ApiResponse<List<CategoryResponse>>> getCategories() {
        List<CategoryResponse> categories = categoryService.getCategories();

        return ResponseEntity.ok(new ApiResponse<>(categories));
    }

    @GetMapping("/{id}")
    ResponseEntity<ApiResponse<List<CategoryResponse>>> getCategories(@PathVariable Integer id) {
        List<CategoryResponse> categories = categoryService.getCategories(id);

        return ResponseEntity.ok(new ApiResponse<>(categories));
    }

}

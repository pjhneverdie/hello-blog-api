package com.jj.hello_blog.web.category.controller;

import com.jj.hello_blog.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;

import com.jj.hello_blog.web.category.dto.CategoryResponse;
import com.jj.hello_blog.web.category.form.CategoryAddForm;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/category")
@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/add")
    CategoryResponse addCategory(@Valid @RequestBody CategoryAddForm categoryAddForm) {
        return categoryService.addCategory(categoryAddForm);
    }

    @GetMapping("/get")
    List<CategoryResponse> getCategories() {
        return categoryService.getCategories();
    }
}

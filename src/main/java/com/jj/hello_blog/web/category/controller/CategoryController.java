package com.jj.hello_blog.web.category.controller;

import lombok.RequiredArgsConstructor;

import jakarta.validation.Valid;

import com.jj.hello_blog.web.category.dto.CategoryResponse;
import com.jj.hello_blog.web.category.form.CategoryAddForm;

import com.jj.hello_blog.domain.category.service.CategoryService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/category")
@RestController
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/add")
    CategoryResponse addCategory(@Valid @RequestBody CategoryAddForm categoryAddForm) {
        return categoryService.addCategory(categoryAddForm);
    }
}

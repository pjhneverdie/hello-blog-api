package com.jj.hello_blog.web.category.controller;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

import com.jj.hello_blog.web.ControllerTestBase;
import com.jj.hello_blog.web.common.response.ApiResponse;

import com.jj.hello_blog.domain.category.dto.CategoryResponse;
import com.jj.hello_blog.domain.category.service.CategoryService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@WebMvcTest(CategoryController.class)
class CategoryControllerTest extends ControllerTestBase {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    @DisplayName("최상위 카테고리 조회 테스트")
    void getRootCategories() throws Exception {
        // Given
        List<CategoryResponse> categoryResponse = new ArrayList<>();
        categoryResponse.add(new CategoryResponse(1, "root1", "test", null, LocalDateTime.now(), 0));
        categoryResponse.add(new CategoryResponse(2, "root2", "test", null, LocalDateTime.now(), 0));

        when(categoryService.getRootCategories()).thenReturn(categoryResponse);

        // When
        ResultActions resultActions = mockMvc.perform(get("/category"));

        // Then
        ApiResponse<List<CategoryResponse>> apiResponse = new ApiResponse<>(categoryResponse);

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(apiResponse)));
    }

    @Test
    @DisplayName("하위 카테고리 조회 테스트")
    void getSubCategories() throws Exception {
        // Given
        List<CategoryResponse> categoryResponse = new ArrayList<>();
        categoryResponse.add(new CategoryResponse(3, "root1-child1", "test", 1, LocalDateTime.now(), 0));
        categoryResponse.add(new CategoryResponse(4, "root1-child2", "test", 1, LocalDateTime.now(), 0));

        when(categoryService.getSubCategories(any(int.class))).thenReturn(categoryResponse);

        // When
        ResultActions resultActions = mockMvc.perform(get("/category/1"));

        // Then
        ApiResponse<List<CategoryResponse>> apiResponse = new ApiResponse<>(categoryResponse);

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(apiResponse)));
    }


}
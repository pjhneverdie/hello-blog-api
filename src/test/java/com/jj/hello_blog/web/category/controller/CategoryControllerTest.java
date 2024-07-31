package com.jj.hello_blog.web.category.controller;

import com.jj.hello_blog.domain.category.dto.CategorySaveDto;
import com.jj.hello_blog.domain.category.dto.CategoryUpdateDto;
import com.jj.hello_blog.domain.category.exception.CategoryExceptionCode;
import com.jj.hello_blog.domain.category.service.CategoryService;
import com.jj.hello_blog.domain.category.dto.CategoryResponse;
import com.jj.hello_blog.domain.common.exception.CustomException;
import com.jj.hello_blog.web.common.response.ApiResponse;
import com.jj.hello_blog.web.ControllerTestBase;
import com.jj.hello_blog.web.category.form.CategorySaveForm;

import com.jj.hello_blog.web.category.form.CategoryUpdateForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(CategoryController.class)
class CategoryControllerTest extends ControllerTestBase {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    @DisplayName("카테고리 추가 테스트")
    void saveCategory() throws Exception {
        // Given
        CategorySaveForm categorySaveForm = new CategorySaveForm("test");

        CategoryResponse categoryResponse = new CategoryResponse(1, categorySaveForm.getName(), new ArrayList<>());
        when(categoryService.saveCategory(any(CategorySaveDto.class))).thenReturn(categoryResponse);

        // When
        ResultActions resultActions = mockMvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(categorySaveForm)));

        // Then
        ApiResponse<CategoryResponse> response = new ApiResponse<>(categoryResponse);

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(response)));
    }

    @Test
    @DisplayName("중복 카테고리 추가 테스트")
    void saveDuplicatedCategory() throws Exception {
        // Given
        CategorySaveForm categorySaveForm = new CategorySaveForm("test");

        doThrow(new CustomException(CategoryExceptionCode.DUPLICATED_CATEGORY))
                .when(categoryService)
                .saveCategory(any(CategorySaveDto.class));

        // When
        ResultActions resultActions = mockMvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(categorySaveForm)));

        // Then
        ApiResponse<Void> response = new ApiResponse<>(null);
        response.setMessage(CategoryExceptionCode.DUPLICATED_CATEGORY.message());

        resultActions
                .andExpect(status().is5xxServerError())
                .andExpect(content().json(toJson(response)));
    }

    @Test
    @DisplayName("카테고리 수정 테스트")
    void updateCategory() throws Exception {
        // Given
        CategoryUpdateForm categoryUpdateForm = new CategoryUpdateForm(1, "test");

        when(categoryService.updateCategory(any(CategoryUpdateDto.class))).thenReturn(true);

        // When
        ResultActions resultActions = mockMvc.perform(patch("/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toJson(categoryUpdateForm)));

        // Then
        ApiResponse<Boolean> response = new ApiResponse<>(true);

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(response)));
    }

    @Test
    @DisplayName("카테고리 조회 테스트")
    void findAllCategories() throws Exception {
        // Given
        List<CategoryResponse> categoryResponse = new ArrayList<>();
        categoryResponse.add(new CategoryResponse(1, "test1", new ArrayList<>()));
        categoryResponse.add(new CategoryResponse(2, "test2", new ArrayList<>()));

        when(categoryService.findAllCategories()).thenReturn(categoryResponse);

        // When
        ResultActions resultActions = mockMvc.perform(get("/category"));

        // Then
        ApiResponse<List<CategoryResponse>> response = new ApiResponse<>(categoryResponse);

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(response)));
    }
}
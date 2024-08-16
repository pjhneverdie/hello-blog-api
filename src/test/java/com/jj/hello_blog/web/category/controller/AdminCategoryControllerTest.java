package com.jj.hello_blog.web.category.controller;

import java.time.LocalDateTime;

import com.jj.hello_blog.domain.category.dto.CategoryUpdateResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import org.springframework.http.MediaType;
import org.springframework.http.HttpMethod;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.mock.web.MockMultipartFile;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import com.jj.hello_blog.domain.category.dto.CategoryAddDto;
import com.jj.hello_blog.domain.category.dto.CategoryResponse;
import com.jj.hello_blog.domain.category.dto.CategoryUpdateDto;

import com.jj.hello_blog.domain.category.service.CategoryService;
import com.jj.hello_blog.domain.category.exception.CategoryExceptionCode;

import com.jj.hello_blog.domain.common.exception.CustomException;

import com.jj.hello_blog.web.ControllerTestBase;
import com.jj.hello_blog.web.common.response.ApiResponse;

import com.jj.hello_blog.web.category.form.CategoryAddForm;
import com.jj.hello_blog.web.category.form.CategoryUpdateForm;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(AdminCategoryController.class)
class AdminCategoryControllerTest extends ControllerTestBase {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    @DisplayName("카테고리 추가 테스트")
    void addCategory() throws Exception {
        // Given
        CategoryAddForm categoryAddForm = new CategoryAddForm("test", null);

        MockMultipartFile jsonFile = new MockMultipartFile("categoryAddForm", "categoryAddForm", "application/json", toJson(categoryAddForm).getBytes());
        MockMultipartFile thumbImageFile = new MockMultipartFile("thumbImageFile", "test.png", "image/png", "image-data".getBytes());

        CategoryResponse categoryResponse = new CategoryResponse(1, categoryAddForm.getName(), thumbImageFile.getName(), null, LocalDateTime.now(), 0);
        when(categoryService.addCategory(any(CategoryAddDto.class))).thenReturn(categoryResponse);

        // When
        ResultActions resultActions = mockMvc.perform(multipart("/admin/category")
                .file(thumbImageFile)
                .file(jsonFile)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE));

        // Then
        ApiResponse<CategoryResponse> response = new ApiResponse<>(categoryResponse);

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(response)));
    }


    @Test
    @DisplayName("중복 카테고리 추가 테스트")
    void addDuplicatedCategory() throws Exception {
        // Given
        CategoryAddForm categoryAddForm = new CategoryAddForm("test", null);

        MockMultipartFile jsonFile = new MockMultipartFile("categoryAddForm", "categoryAddForm", "application/json", toJson(categoryAddForm).getBytes());
        MockMultipartFile thumbImageFile = new MockMultipartFile("thumbImageFile", "test.png", "image/png", "image-data".getBytes());

        doThrow(new CustomException(CategoryExceptionCode.DUPLICATED_CATEGORY))
                .when(categoryService)
                .addCategory(any(CategoryAddDto.class));

        // When
        ResultActions resultActions = mockMvc.perform(multipart("/admin/category")
                .file(thumbImageFile)
                .file(jsonFile)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE));

        // Then
        ApiResponse<Void> response = new ApiResponse<>(null);
        response.setExceptionCode(CategoryExceptionCode.DUPLICATED_CATEGORY.code());

        resultActions
                .andExpect(status().is4xxClientError())
                .andExpect(content().json(toJson(response)));
    }

    @Test
    @DisplayName("카테고리 수정 테스트")
    void updateCategory() throws Exception {
        // Given
        CategoryUpdateForm categoryUpdateForm = new CategoryUpdateForm(1, "test", "test", null);

        MockMultipartFile jsonFile = new MockMultipartFile("categoryUpdateForm", "categoryUpdateForm", "application/json", toJson(categoryUpdateForm).getBytes());
        MockMultipartFile thumbImageFile = new MockMultipartFile("thumbImageFile", "test.png", "image/png", "image-data".getBytes());

        CategoryUpdateResponse categoryUpdateResponse = new CategoryUpdateResponse("updatedThumbUrl");

        when(categoryService.updateCategory(any(CategoryUpdateDto.class))).thenReturn(categoryUpdateResponse);

        // When
        ResultActions resultActions = mockMvc.perform(
                multipart(HttpMethod.PATCH, "/admin/category")
                        .file(thumbImageFile)
                        .file(jsonFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
        );

        // Then
        ApiResponse<CategoryUpdateResponse> response = new ApiResponse<>(categoryUpdateResponse);

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(response)));
    }

    @Test
    @DisplayName("카테고리 삭제 테스트")
    void deleteCategory() throws Exception {
        // Given
        when(categoryService.deleteCategory(any(int.class))).thenReturn(true);

        // When
        ResultActions resultActions = mockMvc.perform(delete("/admin/category/1"));

        // Then
        ApiResponse<Boolean> response = new ApiResponse<>(true);

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(response)));
    }


}
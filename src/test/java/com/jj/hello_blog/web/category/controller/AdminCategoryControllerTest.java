package com.jj.hello_blog.web.category.controller;

import java.time.LocalDateTime;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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

        MockMultipartFile jsonFile = createMockFormFile(categoryAddForm, "categoryAddForm");
        MockMultipartFile thumbImageFile = createMockImageFile("thumbImageFile", "jpeg");

        CategoryResponse categoryResponse = new CategoryResponse(1, categoryAddForm.getName(), thumbImageFile.getName(), null, LocalDateTime.now(), 0);

        when(categoryService.addCategory(any(CategoryAddDto.class))).thenReturn(1);
        when(categoryService.getCategoryAndPostCount(any(int.class))).thenReturn(categoryResponse);

        // When
        ResultActions resultActions = mockMvc.perform(multipart("/admin/category")
                .file(thumbImageFile)
                .file(jsonFile)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .session(createMockHttpSessionWithMember(createMember("test@test.com", "123456"))));

        // Then
        ApiResponse<CategoryResponse> apiResponse = new ApiResponse<>(categoryResponse);

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(apiResponse)));
    }

    @Test
    @DisplayName("카테고리 중복 테스트")
    void addDuplicatedCategory() throws Exception {
        // Given
        CategoryAddForm categoryAddForm = new CategoryAddForm("test", null);

        MockMultipartFile jsonFile = createMockFormFile(categoryAddForm, "categoryAddForm");
        MockMultipartFile thumbImageFile = createMockImageFile("thumbImageFile", "jpeg");

        doThrow(new CustomException(CategoryExceptionCode.DUPLICATED_CATEGORY))
                .when(categoryService)
                .addCategory(any(CategoryAddDto.class));

        // When
        ResultActions resultActions = mockMvc.perform(multipart("/admin/category")
                .file(thumbImageFile)
                .file(jsonFile)
                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                .session(createMockHttpSessionWithMember(createMember("test@test.com", "123456"))));

        // Then
        ApiResponse<Void> apiResponse = new ApiResponse<>(null);
        apiResponse.setExceptionCode(CategoryExceptionCode.DUPLICATED_CATEGORY.code());

        resultActions
                .andExpect(status().is4xxClientError())
                .andExpect(content().json(toJson(apiResponse)));
    }

    @Test
    @DisplayName("카테고리 수정 테스트")
    void updateCategory() throws Exception {
        // Given
        CategoryUpdateForm categoryUpdateForm = new CategoryUpdateForm(1, "test", "thumbUrl", null);

        MockMultipartFile jsonFile = createMockFormFile(categoryUpdateForm, "categoryUpdateForm");
        MockMultipartFile thumbImageFile = createMockImageFile("thumbImageFile", "jpeg");

        CategoryResponse categoryResponse = new CategoryResponse(categoryUpdateForm.getId(), categoryUpdateForm.getName(), thumbImageFile.getName(), categoryUpdateForm.getParentId(), LocalDateTime.now(), 0);

        doNothing().when(categoryService).updateCategory(any(CategoryUpdateDto.class));

        when(categoryService.getCategoryAndPostCount(any(int.class))).thenReturn(categoryResponse);

        // When
        ResultActions resultActions = mockMvc.perform(
                multipart(HttpMethod.PATCH, "/admin/category")
                        .file(thumbImageFile)
                        .file(jsonFile)
                        .contentType(MediaType.MULTIPART_FORM_DATA_VALUE)
                        .session(createMockHttpSessionWithMember(createMember("test@test.com", "123456"))));

        // Then
        ApiResponse<CategoryResponse> apiResponse = new ApiResponse<>(categoryResponse);

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(apiResponse)));
    }

    @Test
    @DisplayName("카테고리 삭제 테스트")
    void deleteCategory() throws Exception {
        // Given
        doNothing().when(categoryService).deleteCategory(any(int.class));

        // When
        ResultActions resultActions = mockMvc.perform(
                delete("/admin/category/1")
                        .session(createMockHttpSessionWithMember(createMember("test@test.com", "123456"))));

        // Then
        ApiResponse<Void> apiResponse = new ApiResponse<>(null);

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().json(toJson(apiResponse)));
    }

}
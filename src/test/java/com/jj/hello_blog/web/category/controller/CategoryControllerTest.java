package com.jj.hello_blog.web.category.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jj.hello_blog.domain.category.service.CategoryService;
import com.jj.hello_blog.web.category.dto.CategoryResponse;
import com.jj.hello_blog.web.category.form.CategoryAddForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    void addCategory() throws Exception {
        // Given
        CategoryAddForm categoryAddForm = new CategoryAddForm("test", null);

        ObjectMapper objectMapper = new ObjectMapper();
        String categoryAddFormJson = objectMapper.writeValueAsString(categoryAddForm);

        CategoryResponse categoryResponse = new CategoryResponse(1, categoryAddForm.getName());
        when(categoryService.addCategory(any(CategoryAddForm.class))).thenReturn(categoryResponse);

        // When
        ResultActions resultActions = mockMvc.perform(post("/category/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoryAddFormJson));

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(categoryAddForm.getName()));
    }

}
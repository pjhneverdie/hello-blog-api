package com.jj.hello_blog.web.category.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jj.hello_blog.domain.category.service.CategoryService;
import com.jj.hello_blog.domain.category.dto.CategoryResponse;
import com.jj.hello_blog.web.category.form.CategorySaveForm;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
        CategorySaveForm categorySaveForm = new CategorySaveForm("test");

        ObjectMapper objectMapper = new ObjectMapper();
        String categoryAddFormJson = objectMapper.writeValueAsString(categorySaveForm);

        CategoryResponse categoryResponse = new CategoryResponse(1, categorySaveForm.getName(), new ArrayList<>());
        when(categoryService.addCategory(any(CategorySaveForm.class))).thenReturn(categoryResponse);

        // When
        ResultActions resultActions = mockMvc.perform(post("/category/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoryAddFormJson));

        // Then
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(categorySaveForm.getName()));
    }

//    @Test
//    void getCategories() throws Exception {
//        // Given
//        when(categoryService.getCategories()).thenReturn(getDummyCategories());
//
//        // When
//        ResultActions resultActions = mockMvc.perform(get("/category/get"));
//
//        // Then
//        resultActions.andExpect(status().isOk())
//                .andExpect(jsonPath("$[0].id").value(1))
//                .andExpect(jsonPath("$[0].name").value("root1"))
//                .andExpect(jsonPath("$[0].children[0].id").value(2))
//                .andExpect(jsonPath("$[0].children[0].name").value("root1-child1"))
//                .andExpect(jsonPath("$[0].children[1].id").value(3))
//                .andExpect(jsonPath("$[0].children[1].name").value("root1-child2"))
//                .andExpect(jsonPath("$[1].id").value(4))
//                .andExpect(jsonPath("$[1].name").value("root2"))
//                .andExpect(jsonPath("$[1].children[0].id").value(5))
//                .andExpect(jsonPath("$[1].children[0].name").value("root2-child1"))
//                .andExpect(jsonPath("$[1].children[0].children[0].id").value(6))
//                .andExpect(jsonPath("$[1].children[0].children[0].name").value("root2-child1-child1"));
//
//    }



}
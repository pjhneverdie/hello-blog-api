package com.jj.hello_blog.web.category.controller;

import com.jj.hello_blog.domain.category.service.CategoryService;
import com.jj.hello_blog.web.ControllerTestBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;


@WebMvcTest(CategoryController.class)
class CategoryControllerTest extends ControllerTestBase {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

//    @Test
//    @DisplayName("카테고리 추가 테스트")
//    void addCategory() throws Exception {
//        // Given
//        CategoryAddForm categoryAddForm = new CategoryAddForm("test", null);
//
//        MockMultipartFile jsonFile = new MockMultipartFile("categoryAddForm", "categoryAddForm", "application/json", toJson(categoryAddForm).getBytes());
//        MockMultipartFile thumbImageFile = new MockMultipartFile("thumbImageFile", "test.png", "image/png", "image-data".getBytes());
//
//        CategoryResponse categoryResponse = new CategoryResponse(1, "thumbUrl", categoryAddForm.getName(), 0);
//        when(categoryService.addCategory(any(CategoryAddDto.class))).thenReturn(categoryResponse);
//
//        // When
//        ResultActions resultActions = mockMvc.perform(multipart("/category")
//                .file(thumbImageFile)
//                .file(jsonFile)
//                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE));
//
//        // Then
//        ApiResponse<CategoryResponse> response = new ApiResponse<>(categoryResponse);
//
//        resultActions
//                .andExpect(status().isOk())
//                .andExpect(content().json(toJson(response)));
//    }
//
//    @Test
//    @DisplayName("중복 카테고리 추가 테스트")
//    void addDuplicatedCategory() throws Exception {
//        // Given
//        CategoryAddForm categoryAddForm = new CategoryAddForm("test", null);
//
//        MockMultipartFile jsonFile = new MockMultipartFile("categoryAddForm", "categoryAddForm", "application/json", toJson(categoryAddForm).getBytes());
//        MockMultipartFile thumbImageFile = new MockMultipartFile("thumbImageFile", "test.png", "image/png", "image-data".getBytes());
//
//        doThrow(new CustomException(CategoryExceptionCode.DUPLICATED_CATEGORY))
//                .when(categoryService)
//                .addCategory(any(CategoryAddDto.class));
//
//        // When
//        ResultActions resultActions = mockMvc.perform(multipart("/category")
//                .file(thumbImageFile)
//                .file(jsonFile)
//                .contentType(MediaType.MULTIPART_FORM_DATA_VALUE));
//
//        // Then
//        ApiResponse<Void> response = new ApiResponse<>(null);
//        response.setExceptionCode(CategoryExceptionCode.DUPLICATED_CATEGORY.code());
//
//        resultActions
//                .andExpect(status().is4xxClientError())
//                .andExpect(content().json(toJson(response)));
//    }
//
//    @Test
//    @DisplayName("전체 카테고리 계층구조 조회 테스트")
//    void findCategoryHierarchy() throws Exception {
//        // Given
//        List<CategoryHierarchyResponse> categoryHierarchyResponses = createCategoryHierarchyResponses();
//
//        when(categoryService.findCategoryHierarchy()).thenReturn(categoryHierarchyResponses);
//
//        // When
//        ResultActions resultActions = mockMvc.perform(get("/category/hierarchy"));
//
//        // Then
//        ApiResponse<List<CategoryHierarchyResponse>> response = new ApiResponse<>(categoryHierarchyResponses);
//
//        resultActions
//                .andExpect(status().isOk())
//                .andExpect(content().json(toJson(response)));
//    }
//
//    @Test
//    @DisplayName("최상위 카테고리 조회 테스트")
//    void findSuperParentCategories() throws Exception {
//        // Given
//        List<CategoryResponse> categoryResponse = new ArrayList<>();
//        categoryResponse.add(new CategoryResponse(1, "thumbUrl", "root1", 0));
//        categoryResponse.add(new CategoryResponse(2, "thumbUrl", "root2", 0));
//
//        when(categoryService.getCategories()).thenReturn(categoryResponse);
//
//        // When
//        ResultActions resultActions = mockMvc.perform(get("/category"));
//
//        // Then
//        ApiResponse<List<CategoryResponse>> response = new ApiResponse<>(categoryResponse);
//
//        resultActions
//                .andExpect(status().isOk())
//                .andExpect(content().json(toJson(response)));
//    }
//
//    @Test
//    @DisplayName("하위 카테고리 조회 테스트")
//    void findChildCategories() throws Exception {
//        // Given
//        List<CategoryResponse> categoryResponse = new ArrayList<>();
//        categoryResponse.add(new CategoryResponse(1, "thumbUrl", "root1", 0));
//        categoryResponse.add(new CategoryResponse(2, "thumbUrl", "root2", 0));
//
//        when(categoryService.getCategories(any(int.class))).thenReturn(categoryResponse);
//
//        // When
//        ResultActions resultActions = mockMvc.perform(get("/category/1"));
//
//        // Then
//        ApiResponse<List<CategoryResponse>> response = new ApiResponse<>(categoryResponse);
//
//        resultActions
//                .andExpect(status().isOk())
//                .andExpect(content().json(toJson(response)));
//    }
//
//    @Test
//    @DisplayName("카테고리 수정 테스트")
//    void updateCategory() throws Exception {
//        // Given
//        CategoryUpdateForm categoryUpdateForm = new CategoryUpdateForm(1, "test", null);
//
//        when(categoryService.updateCategory(any(CategoryUpdateDto.class))).thenReturn(true);
//
//        // When
//        ResultActions resultActions = mockMvc.perform(patch("/category")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(toJson(categoryUpdateForm)));
//
//        // Then
//        ApiResponse<Boolean> response = new ApiResponse<>(true);
//
//        resultActions
//                .andExpect(status().isOk())
//                .andExpect(content().json(toJson(response)));
//    }
//
//    @Test
//    @DisplayName("카테고리 삭제 테스트")
//    void deleteCategory() throws Exception {
//        // Given
//        when(categoryService.deleteCategory(any(int.class))).thenReturn(true);
//
//        // When
//        ResultActions resultActions = mockMvc.perform(delete("/category/1"));
//
//        // Then
//        ApiResponse<Boolean> response = new ApiResponse<>(true);
//
//        resultActions
//                .andExpect(status().isOk())
//                .andExpect(content().json(toJson(response)));
//    }

}
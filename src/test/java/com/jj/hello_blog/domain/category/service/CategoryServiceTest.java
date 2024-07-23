package com.jj.hello_blog.domain.category.service;

import com.jj.hello_blog.domain.category.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void getCategories() {
//        // Given
//        when(categoryRepository.getCategories()).thenReturn(getDummyCategories());
//
//        // When
//        List<CategoryResponse> categories = categoryService.getCategories();
//
//        // Then
//        assertNotNull(categories);
//        assertEquals(2, categories.size());
//
//        CategoryResponse root1 = categories.get(0);
//        assertEquals("root1", root1.getName());
//        assertEquals(2, root1.getChildren().size());
//
//        CategoryResponse root1Child1 = root1.getChildren().get(0);
//        assertEquals("root1-child1", root1Child1.getName());
//        assertTrue(root1Child1.getChildren().isEmpty());
//
//        CategoryResponse root1Child2 = root1.getChildren().get(1);
//        assertEquals("root1-child2", root1Child2.getName());
//        assertTrue(root1Child2.getChildren().isEmpty());
//
//        CategoryResponse root2 = categories.get(1);
//        assertEquals("root2", root2.getName());
//        assertEquals(1, root2.getChildren().size());
//
//        CategoryResponse root2Child1 = root2.getChildren().get(0);
//        assertEquals("root2-child1", root2Child1.getName());
//        assertEquals(1, root2Child1.getChildren().size());
//
//        CategoryResponse root2Child1Child1 = root2Child1.getChildren().get(0);
//        assertEquals("root2-child1-child1", root2Child1Child1.getName());
//        assertTrue(root2Child1Child1.getChildren().isEmpty());
//    }

}
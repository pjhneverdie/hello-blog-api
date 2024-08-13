package com.jj.hello_blog.domain.category.repository;

import java.util.List;

import com.jj.hello_blog.domain.category.dto.CategoryHierarchy;
import org.apache.ibatis.annotations.Mapper;

import com.jj.hello_blog.domain.category.dto.Category;
import com.jj.hello_blog.domain.category.dto.CategoryResponse;
import com.jj.hello_blog.domain.category.dto.CategoryUpdateQueryDto;

@Mapper
public interface CategoryMapper {

    void insertCategory(Category category);

    void updateCategoryById(CategoryUpdateQueryDto categoryUpdateQueryDto);

    void deleteCategoryById(int id);

    List<CategoryHierarchy> selectCategoryHierarchy();

    List<CategoryResponse> selectCategoriesWhereParentIdIsNull();

    List<CategoryResponse> selectCategoriesByParentId(int parentId);

}

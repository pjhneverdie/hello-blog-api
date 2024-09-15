package com.jj.hello_blog.domain.category.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jj.hello_blog.domain.category.dto.*;

@Mapper
public interface CategoryMapper {

    void insertCategory(Category category);

    Category selectCategoryById(int id);

    List<Category> selectCategoriesByName(String name);

    List<Category> selectAllSubCategoriesById(int id);

    CategoryResponse selectCategoryAndPostCountJoinPostById(int id);

    List<CategoryResponse> selectCategoriesWhereParentIdIsNull();

    List<CategoryResponse> selectCategoriesByParentId(int parentId);

    void updateCategoryById(CategoryUpdateQueryDto categoryUpdateQueryDto);

    void deleteCategoryById(int id);

}

package com.jj.hello_blog.domain.category.repository;

import com.jj.hello_blog.domain.category.dto.Category;
import com.jj.hello_blog.domain.category.dto.CategoryResponse;
import com.jj.hello_blog.domain.category.dto.CategoryUpdateDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    void saveCategory(Category category);

    void updateCategory(CategoryUpdateDto category);

    void deleteCategory(int id);

    List<CategoryResponse> findAllCategories();

}

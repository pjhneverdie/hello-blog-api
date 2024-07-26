package com.jj.hello_blog.domain.category.repository;

import com.jj.hello_blog.domain.category.dto.Category;
import com.jj.hello_blog.domain.category.dto.CategoryResponse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    void saveCategory(Category category);

    List<CategoryResponse> getCategories();

    void deleteCategoryById(int id);

}

package com.jj.hello_blog.domain.category.repository;

import com.jj.hello_blog.domain.category.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    void addCategory(Category category);

    List<Category> getCategories();

}

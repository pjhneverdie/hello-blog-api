package com.jj.hello_blog.domain.category.repository.mybatis;

import com.jj.hello_blog.domain.category.entity.mybatis.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {

    void addCategory(Category category);

}

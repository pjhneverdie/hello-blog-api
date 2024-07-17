package com.jj.hello_blog.domain.category.repository.mybatis;

import com.jj.hello_blog.domain.category.entity.mybatis.Category;
import com.jj.hello_blog.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MyBatisCategoryRepository implements CategoryRepository {

    private final CategoryMapper categoryMapper;

    @Override
    public void addCategory(Category category) {
        categoryMapper.addCategory(category);
    }
}

package com.jj.hello_blog.domain.category.service;

import com.jj.hello_blog.web.category.dto.CategoryResponse;
import com.jj.hello_blog.web.category.form.CategoryAddForm;

public interface CategoryService {

    CategoryResponse addCategory(CategoryAddForm categoryAddForm);

}

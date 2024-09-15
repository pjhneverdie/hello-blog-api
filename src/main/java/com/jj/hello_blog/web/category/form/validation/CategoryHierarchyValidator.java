package com.jj.hello_blog.web.category.form.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.jj.hello_blog.web.category.form.CategoryUpdateForm;

public class CategoryHierarchyValidator implements ConstraintValidator<CategoryHierarchyConstraint, CategoryUpdateForm> {

    @Override
    public void initialize(CategoryHierarchyConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(CategoryUpdateForm categoryUpdateForm, ConstraintValidatorContext constraintValidatorContext) {
        if(categoryUpdateForm.getId() == null){
            return false;
        }

        // 내 부모가 나일 수는 없음!
        return !categoryUpdateForm.getId().equals(categoryUpdateForm.getParentId());
    }

}

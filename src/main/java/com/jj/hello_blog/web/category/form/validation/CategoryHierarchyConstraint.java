package com.jj.hello_blog.web.category.form.validation;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import jakarta.validation.Constraint;

@Constraint(validatedBy = CategoryHierarchyValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CategoryHierarchyConstraint {
    String message() default "";

    Class<?>[] groups() default {};

    Class[] payload() default {};
}

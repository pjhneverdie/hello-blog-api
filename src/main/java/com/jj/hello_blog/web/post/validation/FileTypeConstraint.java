package com.jj.hello_blog.web.post.validation;

import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FileTypeValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FileTypeConstraint {
    String message() default "지원하지 않는 파일 형식";

    Class<?>[] groups() default {};

    Class[] payload() default {};
}

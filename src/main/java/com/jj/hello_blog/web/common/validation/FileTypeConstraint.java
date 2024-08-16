package com.jj.hello_blog.web.common.validation;

import java.lang.annotation.Target;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import jakarta.validation.Constraint;

@Constraint(validatedBy = FileTypeValidator.class)
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FileTypeConstraint {
    String message() default "지원하지 않는 파일 형식";

    Class<?>[] groups() default {};

    Class[] payload() default {};
}

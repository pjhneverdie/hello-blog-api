package com.jj.hello_blog.web.category.form;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CategoryUpdateFormTest {
    private Validator validator;

    @BeforeEach
    public void setUpValidator() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validateName() {
        CategoryUpdateForm categoryUpdateFormNullName = new CategoryUpdateForm(1, null);
        CategoryUpdateForm categoryUpdateFormBlankName = new CategoryUpdateForm(1, " ");

        // null
        Set<ConstraintViolation<CategoryUpdateForm>> constraintViolations = validator.validate(categoryUpdateFormNullName);
        assertFalse(constraintViolations.isEmpty());

        // empty
        constraintViolations = validator.validate(categoryUpdateFormBlankName);
        assertFalse(constraintViolations.isEmpty());
    }


}
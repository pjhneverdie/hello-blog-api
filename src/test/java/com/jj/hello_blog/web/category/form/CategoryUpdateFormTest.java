package com.jj.hello_blog.web.category.form;

import java.util.Set;

import jakarta.validation.Validator;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class CategoryUpdateFormTest {
    private Validator validator;

    @BeforeEach
    public void setUpValidator() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validateId() {
        CategoryUpdateForm categoryUpdateFormNullId = new CategoryUpdateForm(null, "test", 1);

        Set<ConstraintViolation<CategoryUpdateForm>> constraintViolations = validator.validate(categoryUpdateFormNullId);
        assertFalse(constraintViolations.isEmpty());
    }

    @Test
    void validateName() {
        CategoryUpdateForm categoryUpdateFormNullName = new CategoryUpdateForm(1, null, 1);
        CategoryUpdateForm categoryUpdateFormBlankName = new CategoryUpdateForm(1, " ", 1);

        // null
        Set<ConstraintViolation<CategoryUpdateForm>> constraintViolations = validator.validate(categoryUpdateFormNullName);
        assertFalse(constraintViolations.isEmpty());

        // empty
        constraintViolations = validator.validate(categoryUpdateFormBlankName);
        assertFalse(constraintViolations.isEmpty());
    }


}
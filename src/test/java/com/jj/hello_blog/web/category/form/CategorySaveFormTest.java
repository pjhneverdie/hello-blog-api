package com.jj.hello_blog.web.category.form;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CategorySaveFormTest {
    private Validator validator;

    @BeforeEach
    public void setUpValidator() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validateName() {
        CategorySaveForm categorySaveFormNullName = new CategorySaveForm(null);
        CategorySaveForm categorySaveFormBlankName = new CategorySaveForm(" ");

        // null
        Set<ConstraintViolation<CategorySaveForm>> constraintViolations = validator.validate(categorySaveFormNullName);
        assertFalse(constraintViolations.isEmpty());

        // empty
        constraintViolations = validator.validate(categorySaveFormBlankName);
        assertFalse(constraintViolations.isEmpty());
    }

}
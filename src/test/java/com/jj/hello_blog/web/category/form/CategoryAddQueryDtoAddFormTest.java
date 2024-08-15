package com.jj.hello_blog.web.category.form;

import java.util.Set;

import jakarta.validation.Validator;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class CategoryAddQueryDtoAddFormTest {
    private Validator validator;

    @BeforeEach
    public void setUpValidator() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validateName() {
        CategoryAddForm categoryAddFormNullName = new CategoryAddForm(null, 1);
        CategoryAddForm categoryAddFormBlankName = new CategoryAddForm(" ", 1);

        // null
        Set<ConstraintViolation<CategoryAddForm>> constraintViolations = validator.validate(categoryAddFormNullName);
        assertFalse(constraintViolations.isEmpty());

        // empty
        constraintViolations = validator.validate(categoryAddFormBlankName);
        assertFalse(constraintViolations.isEmpty());
    }

}
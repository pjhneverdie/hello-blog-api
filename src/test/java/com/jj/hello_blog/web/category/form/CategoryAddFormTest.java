package com.jj.hello_blog.web.category.form;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CategoryAddFormTest {
    private Validator validator;

    @BeforeEach
    public void setUpValidator() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validateName() {
        CategoryAddForm categoryAddFormNullName = new CategoryAddForm(null, null);
        CategoryAddForm categoryAddFormBlankName = new CategoryAddForm(" ", null);

        // null
        Set<ConstraintViolation<CategoryAddForm>> constraintViolations = validator.validate(categoryAddFormNullName);
        assertFalse(constraintViolations.isEmpty());

        // empty
        constraintViolations = validator.validate(categoryAddFormBlankName);
        assertFalse(constraintViolations.isEmpty());
    }

}
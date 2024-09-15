package com.jj.hello_blog.web.post.form;

import java.util.Set;

import jakarta.validation.Validator;
import jakarta.validation.Validation;
import jakarta.validation.ConstraintViolation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class PostSaveFormTest {

    private Validator validator;

    @BeforeEach
    public void setUpValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void validateTitle() {
        PostSaveForm postSaveFormNullTitle = new PostSaveForm(null, "test", 1);
        PostSaveForm postSaveFormBlankTitle = new PostSaveForm(" ", "test", 1);
        PostSaveForm postSaveFormOverSizeTitle = new PostSaveForm(
                "123456789" + "123456789" + "123456789" + "123456789" + "123456789" + "123456",
                "test",
                1);

        // null
        Set<ConstraintViolation<PostSaveForm>> violations = validator.validate(postSaveFormNullTitle);
        assertFalse(violations.isEmpty());

        // blank
        violations = validator.validate(postSaveFormBlankTitle);
        assertFalse(violations.isEmpty());

        // over
        violations = validator.validate(postSaveFormOverSizeTitle);
        assertFalse(violations.isEmpty());
    }

}
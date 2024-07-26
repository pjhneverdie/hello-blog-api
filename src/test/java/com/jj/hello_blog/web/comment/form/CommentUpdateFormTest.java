package com.jj.hello_blog.web.comment.form;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CommentUpdateFormTest {

    private Validator validator;

    @BeforeEach
    public void setUpValidator() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validateContent() {
        CommentUpdateForm commentUpdateFormNullContent = new CommentUpdateForm(1, null);
        CommentUpdateForm commentUpdateFormBlankContent = new CommentUpdateForm(1, " ");

        // null
        Set<ConstraintViolation<CommentUpdateForm>> violations = validator.validate(commentUpdateFormNullContent);
        assertFalse(violations.isEmpty());

        // blank
        violations = validator.validate(commentUpdateFormBlankContent);
        assertFalse(violations.isEmpty());
    }

}
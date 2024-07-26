package com.jj.hello_blog.web.comment.form;

import com.jj.hello_blog.web.post.form.PostSaveForm;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CommentSaveFormTest {

    private Validator validator;

    @BeforeEach
    public void setUpValidator() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void validateContent() {
        CommentSaveForm commentSaveFormNullContent = new CommentSaveForm(null, 1, 1, 1);
        CommentSaveForm commentSaveFormBlankContent = new CommentSaveForm(" ", 1, 1, 1);

        // null
        Set<ConstraintViolation<CommentSaveForm>> violations = validator.validate(commentSaveFormNullContent);
        assertFalse(violations.isEmpty());

        // blank
        violations = validator.validate(commentSaveFormBlankContent);
        assertFalse(violations.isEmpty());
    }

}
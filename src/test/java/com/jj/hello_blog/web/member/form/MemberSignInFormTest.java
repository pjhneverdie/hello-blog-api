package com.jj.hello_blog.web.member.form;

import java.util.Set;

import jakarta.validation.Validator;
import jakarta.validation.Validation;
import jakarta.validation.ConstraintViolation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

class MemberSignInFormTest {

    private Validator validator;

    @BeforeEach
    public void setUpValidator() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @Test
    public void validateEmail() {
        MemberSignInForm signInFormNullEmail = new MemberSignInForm(null, "123456");
        MemberSignInForm signInFormNotEmail = new MemberSignInForm("testtest", "123456");

        // null
        Set<ConstraintViolation<MemberSignInForm>> violations = validator.validate(signInFormNullEmail);
        assertFalse(violations.isEmpty());

        // not email
        violations = validator.validate(signInFormNotEmail);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void validatePassword() {
        MemberSignInForm signInFormNullPassword = new MemberSignInForm("test@test.com", null);
        MemberSignInForm signInFormBlankPassword = new MemberSignInForm("test@test.com", " ");
        MemberSignInForm signInFormUnderSizePassword = new MemberSignInForm("test@test.com", "12345");

        // null
        Set<ConstraintViolation<MemberSignInForm>> violations = validator.validate(signInFormNullPassword);
        assertFalse(violations.isEmpty());

        // blank
        violations = validator.validate(signInFormBlankPassword);
        assertFalse(violations.isEmpty());

        // under
        violations = validator.validate(signInFormUnderSizePassword);
        assertFalse(violations.isEmpty());
    }

}
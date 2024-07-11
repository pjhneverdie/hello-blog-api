package com.jj.hello_blog.web.member.form.model;

import com.jj.hello_blog.web.member.form.SignInForm;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SignInFormTest {
    private Validator validator;

    @BeforeEach
    public void setUpValidator() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validateEmail() {
        SignInForm signInFormNullEmail = new SignInForm(null, "123456");
        SignInForm signInFormNotEmail = new SignInForm("testtest", "123456");

        // null
        Set<ConstraintViolation<SignInForm>> violations = validator.validate(signInFormNullEmail);
        assertFalse(violations.isEmpty());

        // not email
        violations = validator.validate(signInFormNotEmail);
        assertFalse(violations.isEmpty());
    }

    @Test
    public void validatePassword() {
        SignInForm signInFormNullPassword = new SignInForm("test@test.com", null);
        SignInForm signInFormBlankPassword = new SignInForm("test@test.com", "      ");
        SignInForm signInFormUnderSizePassword = new SignInForm("test@test.com", "12345");

        // null
        Set<ConstraintViolation<SignInForm>> violations = validator.validate(signInFormNullPassword);
        assertFalse(violations.isEmpty());

        // blank
        violations = validator.validate(signInFormBlankPassword);
        assertFalse(violations.isEmpty());

        // under
        violations = validator.validate(signInFormUnderSizePassword);
        assertFalse(violations.isEmpty());
    }

}
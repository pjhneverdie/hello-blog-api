package com.jj.hello_blog.web.member.form;

import com.jj.hello_blog.web.member.form.group.save.MemberSaveGroup;
import com.jj.hello_blog.web.member.form.group.update.MemberUpdateGroup;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.validation.Validator;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.constraints.NotNull;

import java.lang.reflect.Field;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MemberFormTest {
    private Validator validator;

    @BeforeEach
    public void setUpValidator() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void validateId() throws NoSuchFieldException {
        // Given
        MemberForm memberForm = new MemberForm(null, "test@test.com", "123456");
        Field field = MemberForm.class.getDeclaredField("id");

        /**
         * When MemberUpdateGroup
         * id가 null일 때 벨리데이션에 실패해야 함
         */
        Set<ConstraintViolation<MemberForm>> violations = validator.validate(memberForm, MemberUpdateGroup.class);

        // Then
        boolean result = violations.stream().allMatch(violation -> violation.getMessage().equals(field.getAnnotation(NotNull.class).message()));
        assertTrue(result);

        /**
         * When MemberSaveGroup
         * id가 null이어도 벨리데이션에 성공해야 함
         */
        violations = validator.validate(memberForm, MemberSaveGroup.class);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    public void validatePassword() throws NoSuchFieldException {
        MemberForm memberFormNullPassword = new MemberForm(1, "test@test.com", null);
        MemberForm memberFormBlankPassword = new MemberForm(1, "test@test.com", "      ");
        MemberForm memberFormUnderSizePassword = new MemberForm(1, "test@test.com", "12345");

        Field field = MemberForm.class.getDeclaredField("password");

        String notNullMessage = field.getAnnotation(NotNull.class).message();
        String notBlankMessage = field.getAnnotation(NotBlank.class).message();
        String sizeMessage = field.getAnnotation(Size.class).message();

        // null
        Set<ConstraintViolation<MemberForm>> violations = validator.validate(memberFormNullPassword, MemberSaveGroup.class);
        boolean result = violations.stream().allMatch(violation -> violation.getMessage().equals(notNullMessage));
        assertTrue(result);

        // blank
        violations = validator.validate(memberFormBlankPassword, MemberSaveGroup.class);
        result = violations.stream().allMatch(violation -> violation.getMessage().equals(notBlankMessage));
        assertTrue(result);

        // under
        violations = validator.validate(memberFormUnderSizePassword, MemberSaveGroup.class);
        result = violations.stream().allMatch(violation -> violation.getMessage().equals(sizeMessage));
        assertTrue(result);
    }

    @Test
    public void validateEmail() throws NoSuchFieldException {
        MemberForm memberFormNullEmail = new MemberForm(1, null, "123456");
        MemberForm memberFormNotEmail = new MemberForm(1, "testtest", "123456");

        String notNullMessage = MemberForm.class.getDeclaredField("email").getAnnotation(NotNull.class).message();
        String notEmailMessage = MemberForm.class.getDeclaredField("email").getAnnotation(Email.class).message();

        // null
        Set<ConstraintViolation<MemberForm>> violations = validator.validate(memberFormNullEmail, MemberSaveGroup.class);
        boolean result = violations.stream().allMatch(violation -> violation.getMessage().equals(notNullMessage));
        assertTrue(result);

        // not email
        violations = validator.validate(memberFormNotEmail, MemberSaveGroup.class);
        result = violations.stream().allMatch(violation -> violation.getMessage().equals(notEmailMessage));
        assertTrue(result);
    }
}

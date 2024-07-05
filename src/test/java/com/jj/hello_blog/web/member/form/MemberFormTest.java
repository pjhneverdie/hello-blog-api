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
        MemberForm memberForm = new MemberForm(null, "name", "email@email.com");
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
    public void validateName() throws NoSuchFieldException {
        MemberForm memberFormNullName = new MemberForm(1, null, "email@email.com");
        MemberForm memberFormBlankName = new MemberForm(1, null, "email@email.com");
        MemberForm memberFormOverSizeName = new MemberForm(
                1, "12345678910111213141516", "email@email.com");
        MemberForm memberFormUnderSizeName = new MemberForm(1, "1", "email@email.com");

        Field field = MemberForm.class.getDeclaredField("name");

        String notNullMessage = field.getAnnotation(NotNull.class).message();
        String notBlankMessage = field.getAnnotation(NotBlank.class).message();
        String sizeMessage = field.getAnnotation(Size.class).message();

        // null
        Set<ConstraintViolation<MemberForm>> violations = validator.validate(memberFormNullName, MemberSaveGroup.class);
        boolean result = violations.stream().allMatch(violation -> violation.getMessage().equals(notNullMessage));
        assertTrue(result);

        // blank
        violations = validator.validate(memberFormBlankName, MemberSaveGroup.class);
        result = violations.stream().allMatch(violation -> violation.getMessage().equals(notBlankMessage));
        assertTrue(result);

        // over
        violations = validator.validate(memberFormOverSizeName, MemberSaveGroup.class);
        result = violations.stream().allMatch(violation -> violation.getMessage().equals(sizeMessage));
        assertTrue(result);

        // under
        violations = validator.validate(memberFormUnderSizeName, MemberSaveGroup.class);
        result = violations.stream().allMatch(violation -> violation.getMessage().equals(sizeMessage));
        assertTrue(result);
    }

    @Test
    public void validateEmail() throws NoSuchFieldException {
        // Given
        MemberForm memberFormNotEmail = new MemberForm(1, "name", "email");
        String notEmailMessage = MemberForm.class.getDeclaredField("email").getAnnotation(Email.class).message();

        // When
        Set<ConstraintViolation<MemberForm>> violations = validator.validate(memberFormNotEmail, MemberSaveGroup.class);

        // Then
        boolean result = violations.stream().allMatch(violation -> violation.getMessage().equals(notEmailMessage));
        assertTrue(result);
    }
}
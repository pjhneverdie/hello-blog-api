package com.jj.hello_blog.web.post.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FileTypeValidatorTest {

    private Validator validator;

    @Mock
    private MultipartFile file;

    private static class MultipartFileContainer {

        @FileTypeConstraint
        private MultipartFile file;

        public void setFile(MultipartFile file) {
            this.file = file;
        }

    }

    @BeforeEach
    public void setUpValidator() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void validateEmptyFile() {
        // Given
        when(file.isEmpty()).thenReturn(true);

        MultipartFileContainer container = new MultipartFileContainer();
        container.setFile(file);

        // When
        Set<ConstraintViolation<MultipartFileContainer>> violations = validator.validate(container);

        // Then
        assertFalse(violations.isEmpty());
    }

    @Test
    public void validateNullOriginalFilename() {
        // Given
        when(file.getOriginalFilename()).thenReturn(null);

        MultipartFileContainer container = new MultipartFileContainer();
        container.setFile(file);

        // When
        Set<ConstraintViolation<MultipartFileContainer>> violations = validator.validate(container);

        // Then
        assertFalse(violations.isEmpty());
    }

    @Test
    public void validateValidJpgFile() {
        // Given
        when(file.getOriginalFilename()).thenReturn("test.jpg");

        MultipartFileContainer container = new MultipartFileContainer();
        container.setFile(file);

        // When
        Set<ConstraintViolation<MultipartFileContainer>> violations = validator.validate(container);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    public void validateValidJpegFile() {
        // Given
        when(file.getOriginalFilename()).thenReturn("test.jpeg");

        MultipartFileContainer container = new MultipartFileContainer();
        container.setFile(file);

        // When
        Set<ConstraintViolation<MultipartFileContainer>> violations = validator.validate(container);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    public void validateValidPngFile() {
        // Given
        when(file.getOriginalFilename()).thenReturn("test.png");

        MultipartFileContainer container = new MultipartFileContainer();
        container.setFile(file);

        // When
        Set<ConstraintViolation<MultipartFileContainer>> violations = validator.validate(container);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    public void validateInvalidFileType() {
        // Given
        when(file.getOriginalFilename()).thenReturn("test.txt");

        MultipartFileContainer container = new MultipartFileContainer();
        container.setFile(file);

        // When
        Set<ConstraintViolation<MultipartFileContainer>> violations = validator.validate(container);

        // Then
        assertFalse(violations.isEmpty());
    }

    @Test
    public void validateNoExtension() {
        // Given
        when(file.getOriginalFilename()).thenReturn("test");

        MultipartFileContainer container = new MultipartFileContainer();
        container.setFile(file);

        // When
        Set<ConstraintViolation<MultipartFileContainer>> violations = validator.validate(container);

        // Then
        assertFalse(violations.isEmpty());
    }
}
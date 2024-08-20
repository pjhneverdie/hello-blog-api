package com.jj.hello_blog.web.common.validation;

import java.util.Set;
import java.util.HashSet;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class FileTypeValidator implements ConstraintValidator<FileTypeConstraint, MultipartFile> {

    private static final Set<String> ALLOWED_EXTENSIONS = new HashSet<>();

    static {
        ALLOWED_EXTENSIONS.add("png");
        ALLOWED_EXTENSIONS.add("jpg");
        ALLOWED_EXTENSIONS.add("jpeg");
    }

    @Override
    public void initialize(FileTypeConstraint constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintValidatorContext) {
        if (file == null) {
            return true;
        }

        String originalFilename = file.getOriginalFilename();

        if (originalFilename == null) {
            return false;
        }

        String[] splitFilename = originalFilename.split("\\.");
        String extension = splitFilename[splitFilename.length - 1].toLowerCase();

        return ALLOWED_EXTENSIONS.contains(extension);
    }
}
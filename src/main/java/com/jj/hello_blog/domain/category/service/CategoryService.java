package com.jj.hello_blog.domain.category.service;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.dao.DataIntegrityViolationException;

import com.jj.hello_blog.domain.category.dto.*;
import com.jj.hello_blog.domain.category.repository.CategoryRepository;
import com.jj.hello_blog.domain.category.exception.CategoryExceptionCode;

import com.jj.hello_blog.domain.common.exception.CustomException;
import com.jj.hello_blog.domain.common.aws.service.S3BucketImageService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final S3BucketImageService s3BucketImageService;

    @Value("${blog.config.category.default.category.thumburl}")
    private String DEFAULT_THUMB_URL;

    /**
     * addCategory, 카테고리 추가
     */
    public int addCategory(CategoryAddDto categoryAddDto) {
        checkDuplicatedCategory(categoryAddDto);

        String thumbUrl = DEFAULT_THUMB_URL;

        // 1. 썸네일 업로드
        // 썸네일은 필수 X
        if (categoryAddDto.getThumbImageFile() != null) {
            thumbUrl = s3BucketImageService.putImage(categoryAddDto.getThumbImageFile());
        }

        Category category = new Category(null, categoryAddDto.getName(), thumbUrl, categoryAddDto.getParentId(), null);

        // 2. 카테고리 인서트(id 필드 채워짐, createdAt은 자동으로 안 채워짐)
        categoryRepository.insertCategory(category);

        return category.getId();
    }

    /**
     * getCategoryAndPostCount, 카테고리랑 카테고리에 게시된 글 수 조인
     */
    public CategoryResponse getCategoryAndPostCount(int id) {
        return categoryRepository.selectCategoryAndPostCountJoinPostById(id);
    }

    /**
     * getRootCategories, 최상위 카테고리 조회
     */
    public List<CategoryResponse> getRootCategories() {
        return categoryRepository.selectCategoriesWhereParentIdIsNull();
    }

    /**
     * getSubCategories, 하위 카테고리 조회
     */
    public List<CategoryResponse> getSubCategories(int parentId) {
        return categoryRepository.selectCategoriesByParentId(parentId);
    }

    /**
     * updateCategory, 카테고리 수정
     */
    public void updateCategory(CategoryUpdateDto categoryUpdateDto) {
        // 이동하려는 카테고리가 존재하는지 확인
        if (categoryUpdateDto.getParentId() != null) {
            checkIsExistCategory(categoryUpdateDto.getParentId());
        }

        // 이동하려는 카테고리가 올바른 계층 구조를 따르는지 확인
        checkIsValidHierarchy(categoryUpdateDto);

        String thumbUrl = categoryUpdateDto.getThumbUrl();

        // 1. 썸네일 업로드
        // 썸네일은 필수 X
        if (categoryUpdateDto.getThumbImageFile() != null) {
            // 기존 썸네일 삭제
            if (!thumbUrl.equals(DEFAULT_THUMB_URL)) {
                s3BucketImageService.deleteImage(thumbUrl);
            }

            thumbUrl = s3BucketImageService.putImage(categoryUpdateDto.getThumbImageFile());
        }

        CategoryUpdateQueryDto categoryUpdateQueryDto = new CategoryUpdateQueryDto(categoryUpdateDto.getId(), categoryUpdateDto.getName(), thumbUrl, categoryUpdateDto.getParentId());

        // 2. 카테고리 수정
        categoryRepository.updateCategoryById(categoryUpdateQueryDto);
    }

    /**
     * deleteCategory, 카테고리 삭제
     */
    public void deleteCategory(int id) {
        try {
            Optional<Category> category = categoryRepository.selectCategoryById(id);

            if (!category.get().getThumbUrl().equals(DEFAULT_THUMB_URL)) {
                s3BucketImageService.deleteImage(category.get().getThumbUrl());
            }

            categoryRepository.deleteCategoryById(id);
        } catch (DataIntegrityViolationException e) {
            // 외래키 제약조건 위배(게시글이 있는데 카테고리를 삭제하려고 한 경우)
            throw new CustomException(CategoryExceptionCode.POSTS_EXIST_YET);
        }
    }

    /**
     * checkDuplicatedCategory, 카테고리 추가 시 중복인지 확인
     */
    private void checkDuplicatedCategory(CategoryAddDto categoryAddDto) {
        List<Category> categories = categoryRepository.selectCategoriesByName(categoryAddDto.getName());

        boolean isDuplicated = categories.stream()
                .anyMatch(category -> Objects.equals(category.getParentId(), categoryAddDto.getParentId()));

        if (isDuplicated) {
            throw new CustomException(CategoryExceptionCode.DUPLICATED_CATEGORY);
        }
    }

    /**
     * checkIsExistCategory, 카테고리 변경 시 존재하는 부모인지 확인
     */
    public void checkIsExistCategory(int id) {
        Optional<Category> category = categoryRepository.selectCategoryById(id);

        //  존재하지 않는 카테고리의 수정 및 옮김
        if (category.isEmpty()) {
            throw new CustomException(CategoryExceptionCode.CATEGORY_NOT_FOUND);
        }
    }

    /**
     * checkIsValidHierarchy, 카테고리 변경 시 올바른 계층 구조를 따르는지 확인
     */
    private void checkIsValidHierarchy(CategoryUpdateDto categoryUpdateDto) {
        // 내 아래 자식들
        List<Category> categories = categoryRepository.selectAllSubCategoriesById(categoryUpdateDto.getId());

        // 지금 이동하려는 카테고리가 내 자식 카테고리인지 확인
        boolean isValidHierarchy = categories.stream().filter(category -> category.getId().equals(categoryUpdateDto.getParentId())).toList().isEmpty();

        // 부모 카테고리는 자식 카테고리로 이동할 수 없음
        // A -> B -> C 계층구조에서, A는 B로 B는 C로 갈 수 없다는 말
        if (!isValidHierarchy) {
            throw new CustomException(CategoryExceptionCode.INVALID_CATEGORY_HIERARCHY);
        }
    }

}

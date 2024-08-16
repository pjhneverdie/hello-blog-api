package com.jj.hello_blog.domain.category.service;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.multipart.MultipartFile;

import com.jj.hello_blog.domain.category.dto.*;
import com.jj.hello_blog.domain.category.repository.CategoryRepository;
import com.jj.hello_blog.domain.category.exception.CategoryExceptionCode;

import com.jj.hello_blog.domain.common.exception.CustomException;
import com.jj.hello_blog.domain.common.aws.service.S3BucketService;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final S3BucketService s3BucketService;

    @Value("${blog.config.category.default.thumburl}")
    private String DEFAULT_THUMB_URL;

    /**
     * addCategory, 카테고리 추가
     */
    public CategoryResponse addCategory(CategoryAddDto categoryAddDto) {
        try {
            String thumbUrl = DEFAULT_THUMB_URL;

            // 1. 썸네일 업로드
            // 썸네일은 필수 X
            if (categoryAddDto.getThumbImageFile() != null) {
                thumbUrl = uploadThumbImage(categoryAddDto.getThumbImageFile());
            }

            Category category = new Category(null, categoryAddDto.getName(), thumbUrl, categoryAddDto.getParentId(), null);

            // 2. 카테고리 인서트(id 필드 채워짐, createdAt은 자동으로 안 채워짐)
            categoryRepository.insertCategory(category);

            // 3. 조회해서 createdAt 컬럼 받아 오기
            Optional<Category> addedCategory = categoryRepository.selectCategoryById(category.getId());

            // 발생 가능성은 희박하나 혹시 몰라서 예외처리
            if (addedCategory.isEmpty()) {
                throw new CustomException(CategoryExceptionCode.CATEGORY_NOT_FOUND);
            }

            return new CategoryResponse(
                    addedCategory.get().getId(),
                    addedCategory.get().getName(),
                    addedCategory.get().getThumbUrl(),
                    addedCategory.get().getParentId(),
                    addedCategory.get().getCreatedAt(),
                    0
            );

        } catch (DuplicateKeyException e) {
            // 카테고리 이름 중복 시
            throw new CustomException(CategoryExceptionCode.DUPLICATED_CATEGORY);
        }
    }

    /**
     * getCategories, 최상위 카테고리 조회
     */
    public List<CategoryResponse> getCategories() {
        return categoryRepository.selectCategoriesWhereParentIdIsNull();
    }

    /**
     * getCategories, 하위 카테고리 조회
     */
    public List<CategoryResponse> getCategories(int parentId) {
        return categoryRepository.selectCategoriesByParentId(parentId);
    }

    /**
     * updateCategory, 카테고리 수정
     */
    public CategoryUpdateResponse updateCategory(CategoryUpdateDto categoryUpdateDto) {
        String thumbUrl = categoryUpdateDto.getThumbUrl();

        // 1. 썸네일 업로드
        // 썸네일은 필수 X
        if (categoryUpdateDto.getThumbImageFile() != null) {
            thumbUrl = uploadThumbImage(categoryUpdateDto.getThumbImageFile());
        }

        CategoryUpdateQueryDto categoryUpdateQueryDto = new CategoryUpdateQueryDto(
                categoryUpdateDto.getId(),
                categoryUpdateDto.getName(),
                thumbUrl,
                categoryUpdateDto.getParentId()
        );

        // 2. 카테고리 수정
        categoryRepository.updateCategoryById(categoryUpdateQueryDto);

        // 나머지 값은 어차피 프론트에도 이미 가지고 있으니 thumbUrl만 리턴
        return new CategoryUpdateResponse(thumbUrl);
    }

    /**
     * deleteCategory, 카테고리 삭제
     */
    public boolean deleteCategory(int id) {
        categoryRepository.deleteCategoryById(id);

        return true;
    }

    /**
     * uploadThumbImage, 썸네일 업로드 공통 메서드
     */
    private String uploadThumbImage(MultipartFile thumbImageFile) {
        return s3BucketService.putS3Object(thumbImageFile);
    }

}

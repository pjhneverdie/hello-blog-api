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
     * 인서트는 프로젝트가 간단해서 커맨드랑 쿼리 분리 스킵
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
            Category addedCategory = categoryRepository.selectCategoryById(category.getId()).get();

            return new CategoryResponse(
                    addedCategory.getId(),
                    addedCategory.getName(),
                    addedCategory.getThumbUrl(),
                    addedCategory.getParentId(),
                    addedCategory.getCreatedAt(),
                    0
            );

        } catch (DuplicateKeyException e) {
            // 카테고리 이름 중복 시
            throw new CustomException(CategoryExceptionCode.DUPLICATED_CATEGORY);
        }
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
        if (categoryUpdateDto.getParentId() != null) {
            Optional<Category> category = categoryRepository.selectCategoryById(categoryUpdateDto.getParentId());

            //  존재하지 않는 카테고리의 하위 카테고리로 옮기는 경우
            if (category.isEmpty()) {
                throw new CustomException(CategoryExceptionCode.CATEGORY_NOT_FOUND);
            }
        }

        // 내 아래 자식들
        List<Category> categories = categoryRepository.selectAllChildrenById(categoryUpdateDto.getId());

        // 지금 이동하려는 카테고리가 내 자식 카테고리인지 확인
        boolean isValidHierarchy = categories.stream().filter(category -> category.getId().equals(categoryUpdateDto.getParentId())).toList().isEmpty();

        // 부모 카테고리는 자식 카테고리로 이동할 수 없음
        // A -> B -> C 계층구조에서, A는 B로 B는 C로 갈 수 없다는 말
        if (!isValidHierarchy) {
            throw new CustomException(CategoryExceptionCode.INVALID_CATEGORY_HIERARCHY);
        }

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

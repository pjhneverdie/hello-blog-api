package com.jj.hello_blog.domain.category.service;

import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.dao.DuplicateKeyException;

import com.jj.hello_blog.domain.category.dto.*;
import com.jj.hello_blog.domain.category.repository.CategoryRepository;
import com.jj.hello_blog.domain.category.exception.CategoryExceptionCode;

import com.jj.hello_blog.domain.common.exception.CustomException;
import com.jj.hello_blog.domain.common.aws.service.S3BucketService;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final S3BucketService s3BucketService;

    private static final String DEFAULT_THUMB_URL = "";

    /**
     * addCategory, 카테고리 추가
     */
    public CategoryResponse addCategory(CategoryAddDto categoryAddDto) {
        try {
            String thumbUrl = uploadThumbImage(categoryAddDto.getThumbImageFile());

            Category category = new Category(null, thumbUrl, categoryAddDto.getName(), categoryAddDto.getParentId());

            categoryRepository.insertCategory(category);

            return new CategoryResponse(
                    category.getId(), category.getThumbUrl(),
                    category.getName(), 0);

        } catch (DuplicateKeyException e) {
            // 카테고리 이름 중복 시
            throw new CustomException(CategoryExceptionCode.DUPLICATED_CATEGORY);
        }
    }


    /**
     * findCategoryHierarchy, 전체 카테고리 게층구조 조회
     */
    public List<CategoryHierarchyResponse> findCategoryHierarchy() {
        List<CategoryHierarchy> categoryHierarchies = categoryRepository.selectCategoryHierarchy();

        Map<Integer, CategoryHierarchyResponse> categoryMap = categoryHierarchies.stream()
                .collect(Collectors.toMap(
                        CategoryHierarchy::getId,
                        ch -> new CategoryHierarchyResponse(ch.getId(), ch.getName(), ch.getThumbUrl())
                ));

        List<CategoryHierarchyResponse> rootCategories = new ArrayList<>();

        for (CategoryHierarchy category : categoryHierarchies) {
            CategoryHierarchyResponse response = categoryMap.get(category.getId());
            Integer parentId = category.getParentId();

            if (parentId == null) {
                rootCategories.add(response);
            } else {
                CategoryHierarchyResponse parent = categoryMap.get(parentId);
                if (parent != null) {
                    parent.getChildren().add(response);
                }
            }
        }

        return rootCategories;
    }

    /**
     * findCategories, 최상위 카테고리 조회
     */
    public List<CategoryResponse> findCategories() {
        return categoryRepository.selectCategoriesWhereParentIdIsNull();
    }

    /**
     * findCategories, 하위 카테고리 조회
     */
    public List<CategoryResponse> findCategories(int parentId) {
        return categoryRepository.selectCategoriesByParentId(parentId);
    }

    /**
     * updateCategory, 카테고리 수정
     */
    public boolean updateCategory(CategoryUpdateDto categoryUpdateDto) {
        String thumbUrl = uploadThumbImage(categoryUpdateDto.getThumbImageFile());


        CategoryUpdateQueryDto categoryUpdateQueryDto = new CategoryUpdateQueryDto(
                categoryUpdateDto.getId(),
                thumbUrl,
                categoryUpdateDto.getName(),
                categoryUpdateDto.getParentId()
        );

        categoryRepository.updateCategoryById(categoryUpdateQueryDto);

        return true;
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
        String thumbUrl;

        if (thumbImageFile == null) {
            thumbUrl = DEFAULT_THUMB_URL;
        } else {
            thumbUrl = s3BucketService.putS3Object(thumbImageFile);
        }

        return thumbUrl;
    }
}

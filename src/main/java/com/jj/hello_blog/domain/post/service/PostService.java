package com.jj.hello_blog.domain.post.service;

import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.Optional;
import java.util.stream.Collectors;

import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import com.jj.hello_blog.domain.common.exception.CustomException;
import com.jj.hello_blog.domain.common.aws.service.S3BucketImageService;

import com.jj.hello_blog.domain.post.dto.*;
import com.jj.hello_blog.domain.post.repository.PostRepository;
import com.jj.hello_blog.domain.post.exception.PostExceptionCode;

import com.jj.hello_blog.domain.category.service.CategoryService;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final S3BucketImageService s3BucketImageService;

    private final CategoryService categoryService;

    @Value("${blog.config.category.default.post.thumburl}")
    private String DEFAULT_THUMB_URL;

    /**
     * uploadContentImageAsTemp, 게시글 본문에 이미지 삽입(임시)
     */
    public String uploadContentImageAsTemp(MultipartFile contentImageFile) {
        return s3BucketImageService.putImage(contentImageFile, "temp/" + contentImageFile.getOriginalFilename() + LocalDateTime.now());
    }

    /**
     * moveContentImagesFromTempToImage, temp 폴더의 이미지를 image 폴더로 옮김
     */
    public Map<String, String> moveContentImagesFromTempToImage(List<String> tempUrls) {
        Map<String, String> resultMap = new HashMap<>();

        for (String tempUrl : tempUrls) {
            String url = s3BucketImageService.moveFromTempToImage(tempUrl);

            resultMap.put(tempUrl, url);
        }

        deleteImages(tempUrls);

        return resultMap;
    }

    /**
     * deleteImages, 이미지 삭제
     */
    public void deleteImages(List<String> urls) {
        s3BucketImageService.deleteImages(urls);
    }

    /**
     * savePost, 게시글 작성
     */
    public int savePost(PostSaveDto postSaveDto) {
        String thumbUrl = DEFAULT_THUMB_URL;

        // 1. 썸네일 업로드
        // 썸네일은 필수 X
        if (postSaveDto.getThumbImageFile() != null) {
            thumbUrl = s3BucketImageService.putImage(postSaveDto.getThumbImageFile());
        }

        Post post = new Post(null, postSaveDto.getTitle(), postSaveDto.getContent(), thumbUrl, postSaveDto.getCategoryId(), null, null);

        // 2. 게시글 인서트(id 필드 채워짐, createdAt, fixedAt은 자동으로 안 채워짐)
        postRepository.insertPost(post);

        return post.getId();
    }

    /**
     * getPost, 게시글 단건 조회
     */
    public PostResponse getPost(int id) {
        Optional<Post> post = postRepository.selectPostById(id);

        if (post.isEmpty()) {
            throw new CustomException(PostExceptionCode.POST_NOT_FOUND);
        }

        return new PostResponse(
                post.get().getId(),
                post.get().getTitle(),
                post.get().getContent(),
                post.get().getThumbUrl(),
                post.get().getCategoryId(),
                post.get().getFixedAt(),
                post.get().getCreatedAt()
        );
    }

    /**
     * getRecentPosts, 최근 게시글 페이지네이션
     */
    public List<PostResponse> getRecentPosts(PostPaginationCond postPaginationCond) {
        return postRepository.selectPostsOrderByCreatedAtDesc(postPaginationCond);
    }

    /**
     * getPostsInCategory, 카테고리의 게시글 페이지네이션
     */
    public List<PostResponse> getPostsInCategory(int categoryId, PostPaginationCond postPaginationCond) {
        return postRepository.selectPostsByCategoryIdOrderByCreatedAtDesc(categoryId, postPaginationCond);
    }

    /**
     * updatePost, 게시글 수정
     */
    public void updatePost(PostUpdateDto postUpdateDto) {
        // 이동하려는 카테고리가 존재하는지 확인
        try {
            categoryService.checkIsExistCategory(postUpdateDto.getCategoryId());
        } catch (CustomException exception) {
            throw new CustomException(PostExceptionCode.CATEGORY_NOT_FOUND);
        }

        String thumbUrl = postUpdateDto.getThumbUrl();

        // 1. 썸네일 업로드
        // 썸네일은 필수 X
        if (postUpdateDto.getThumbImageFile() != null) {
            // 기존 썸네일 삭제
            if (!thumbUrl.equals(DEFAULT_THUMB_URL)) {
                s3BucketImageService.deleteImage(thumbUrl);
            }

            thumbUrl = s3BucketImageService.putImage(postUpdateDto.getThumbImageFile());
        }

        PostUpdateQueryDto postUpdateQueryDto = new PostUpdateQueryDto(postUpdateDto.getId(), postUpdateDto.getTitle(), postUpdateDto.getContent(), thumbUrl, postUpdateDto.getCategoryId());

        // 2. 게시글 수정
        postRepository.updatePostById(postUpdateQueryDto);
    }

    /**
     * deletePost, 게시글 삭제
     */
    public void deletePost(int id, List<String> relatedUrls) {
        List<String> urlsToDelete = relatedUrls.stream()
                .filter(url -> !url.equals(DEFAULT_THUMB_URL))
                .collect(Collectors.toList());

        if (!urlsToDelete.isEmpty()) {
            // 썸네일, 본문에 삽입한 이미지들 삭제
            deleteImages(urlsToDelete);
        }

        postRepository.deletePostById(id);
    }

}

package com.jj.hello_blog.domain.like.repository;

import com.jj.hello_blog.domain.like.dto.LikeCommentDto;
import com.jj.hello_blog.domain.like.dto.LikePostDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Transactional
class LikeRepositoryTest extends LikeRepositoryTestSetUp {

    @Autowired
    private LikeRepository likeRepository;

    @Test
    @DisplayName("게시글 좋아요 테스트")
    void savePostLike() {
        // Given
        LikePostDto likePostDto = new LikePostDto(member.getId(), post.getId());

        // When
        likeRepository.savePostLike(likePostDto);

        // Then
        int postLikeCount = likeRepository.countLikeById(post.getId(), null);
        assertEquals(1, postLikeCount);
    }

    @Test
    @DisplayName("게시글 총 좋아요 수 조회 테스트")
    void countPostLikeById() {
        // Given
        int beforeLikeCount = likeRepository.countLikeById(post.getId(), null);

        // When
        savePostLike();

        // Then
        int afterLikeCount = likeRepository.countLikeById(post.getId(), null);
        Assertions.assertThat(beforeLikeCount < afterLikeCount).isTrue();
    }

    @Test
    @DisplayName("댓글 좋아요 테스트")
    void saveCommentLike() {
        // Given
        LikeCommentDto likeCommentDto = new LikeCommentDto(member.getId(), comment.getId());

        // When
        likeRepository.saveCommentLike(likeCommentDto);

        // Then
        int countLikeCount = likeRepository.countLikeById(null, comment.getId());
        assertEquals(1, countLikeCount);
    }

    @Test
    @DisplayName("댓글 총 좋아요 수 조회 테스트")
    void countCommentLikeById() {
        // Given
        int beforeLikeCount = likeRepository.countLikeById(null, comment.getId());

        // When
        saveCommentLike();

        // Then
        int afterLikeCount = likeRepository.countLikeById(null, comment.getId());
        Assertions.assertThat(beforeLikeCount < afterLikeCount).isTrue();
    }

}
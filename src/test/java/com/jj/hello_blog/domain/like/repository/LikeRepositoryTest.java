package com.jj.hello_blog.domain.like.repository;

import com.jj.hello_blog.domain.comment.dto.Comment;
import com.jj.hello_blog.domain.like.dto.Like;
import com.jj.hello_blog.domain.member.dto.Member;
import com.jj.hello_blog.domain.post.dto.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
class LikeRepositoryTest extends LikeRepositorySetUpTest {

    @Autowired
    private LikeRepository likeRepository;

    @Test
    @DisplayName("게시글 좋아요 테스트")
    void savePostLike() {
        // Given
        Like like = getLike(member, post, null);

        // When
        likeRepository.saveLike(like);

        // Then
        assertNotNull(like.getId());
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
        Like like = getLike(member, post, comment);

        // When
        likeRepository.saveLike(like);

        // Then
        assertNotNull(like.getId());
    }

    @Test
    @DisplayName("댓글 총 좋아요 수 조회 테스트")
    void countCommentLikeById() {
        // Given
        int beforeLikeCount = likeRepository.countLikeById(post.getId(), comment.getId());

        // When
        saveCommentLike();

        // Then
        int afterLikeCount = likeRepository.countLikeById(post.getId(), comment.getId());
        Assertions.assertThat(beforeLikeCount < afterLikeCount).isTrue();
    }


    /**
     * getLike, 좋아요 데이터 생성 유틸
     *
     * @return 좋아요 데이터
     */
    private Like getLike(Member member, Post post, Comment comment) {
        return new Like(null, member.getId(), post.getId(), comment != null ? comment.getId() : null);
    }
}
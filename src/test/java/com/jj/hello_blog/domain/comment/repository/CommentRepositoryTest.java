package com.jj.hello_blog.domain.comment.repository;

import com.jj.hello_blog.domain.comment.dto.Comment;
import com.jj.hello_blog.domain.comment.dto.CommentUpdateDto;
import com.jj.hello_blog.domain.member.dto.Member;
import com.jj.hello_blog.domain.post.dto.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
public class CommentRepositoryTest extends CommentRepositoryTestSetUp {

    @Autowired
    private CommentRepository commentRepository;

    @Test
    @DisplayName("댓글 작성 테스트")
    void saveComment() {
        // Given
        Comment comment = getComment(member, post);

        // When
        commentRepository.saveComment(comment);

        // Then
        assertNotNull(comment.getId());
    }

    @Test
    @DisplayName("답글 작성 테스트")
    void saveReply() {
        // Given
        Comment comment = getComment(member, post);
        commentRepository.saveComment(comment);

        // When
        Comment reply = getReply(member, post, comment);
        commentRepository.saveComment(reply);

        // Then
        assertNotNull(reply.getId());
        Assertions.assertThat(reply.getParentId().equals(comment.getId())).isTrue();
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    void updateComment() {
        // Given
        Comment comment = getComment(member, post);
        commentRepository.saveComment(comment);

        // When
        CommentUpdateDto commentUpdateDto = new CommentUpdateDto(comment.getId(), "", member.getId());
        commentRepository.updateComment(commentUpdateDto);

        // Then
        Optional<Comment> commentById = commentRepository.findCommentById(comment.getId());
        assertTrue(commentById.isPresent());

        commentById.get().getId().equals(commentUpdateDto.getId());
        commentById.get().getContent().equals(commentUpdateDto.getContent());
    }

    @Test
    @DisplayName("댓글 삭제 테스트")
    void deleteComment() {
        // Given
        Comment comment = getComment(member, post);
        commentRepository.saveComment(comment);

        // When
        commentRepository.deleteComment(comment.getId());

        // Then
        Optional<Comment> commentById = commentRepository.findCommentById(comment.getId());
        assertFalse(commentById.isPresent());
    }

    @Test
    @DisplayName("id로 댓글 조회 테스트")
    void findCommentById() {
        // Given
        Comment comment = getComment(member, post);
        commentRepository.saveComment(comment);

        // When
        Optional<Comment> commentById = commentRepository.findCommentById(comment.getId());

        // Then
        assertTrue(commentById.isPresent());
        commentById.get().getId().equals(comment.getId());
    }

    /**
     * getComment, 댓글 데이터 생성 유틸
     */
    public static Comment getComment(Member member, Post post) {
        return new Comment(null, "test", null, member.getId(), post.getId(), null);
    }

    /**
     * getReply, 답글 데이터 생성 유틸
     */
    public static Comment getReply(Member member, Post post, Comment parent) {
        return new Comment(null, "test", null, member.getId(), post.getId(), parent.getId());
    }

}
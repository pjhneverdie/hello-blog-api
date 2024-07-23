package com.jj.hello_blog.domain.comment.repository;

import com.jj.hello_blog.domain.comment.dto.Comment;
import com.jj.hello_blog.domain.member.dto.Member;
import com.jj.hello_blog.domain.post.dto.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
public class CommentRepositoryTest extends CommentRepositorySetUpTest {

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

    /**
     * getComment, 댓글 데이터 생성 유틸
     *
     * @return 댓글 데이터
     */
    public static Comment getComment(Member member, Post post) {
        return new Comment(null, "test", null, member.getId(), post.getId(), null);
    }

    /**
     * getReply, 답글 데이터 생성 유틸
     *
     * @return 답글 데이터
     */
    public static Comment getReply(Member member, Post post, Comment parent) {
        return new Comment(null, "test", null, member.getId(), post.getId(), parent.getId());
    }

}
package com.jj.hello_blog.domain.comment.service;

import com.jj.hello_blog.domain.comment.dto.*;
import com.jj.hello_blog.domain.comment.exception.CommentExceptionCode;
import com.jj.hello_blog.domain.comment.repository.CommentRepository;
import com.jj.hello_blog.domain.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    /**
     * 댓글 작성
     */
    @Transactional
    public CommentResponse saveComment(CommentSaveDto commentSaveDto) {
        Comment comment = new Comment(
                null, commentSaveDto.getContent(), null,
                commentSaveDto.getMember_id(), commentSaveDto.getPostId(), commentSaveDto.getParentId());

        // 1. 저장
        commentRepository.saveComment(comment);

        // 2. createdAt 받아오기
        comment = findCommentById(comment.getId());

        return new CommentResponse(
                comment.getId(), comment.getContent(), comment.getCreatedAt(),
                comment.getMemberId(), comment.getPostId(), comment.getParentId(),
                commentSaveDto.getEmail(), 0);
    }

    /**
     * 댓글 수정
     */
    @Transactional
    public boolean updateComment(CommentUpdateDto commentUpdateDto) {
        Comment commentById = findCommentById(commentUpdateDto.getId());

        // 본인만 삭제할 수 있음
        if (commentById.getMemberId().equals(commentUpdateDto.getMemberId())) {
            commentRepository.updateComment(commentUpdateDto);
            return true;
        }

        throw new CustomException(CommentExceptionCode.UNAUTHORIZED);
    }

    /**
     * 댓글 삭제
     */
    @Transactional
    public boolean deleteComment(CommentDeleteDto commentDeleteDto) {
        Comment commentById = findCommentById(commentDeleteDto.getId());

        // 본인만 삭제할 수 있음
        if (commentById.getMemberId().equals(commentDeleteDto.getMemberId())) {
            commentRepository.deleteComment(commentDeleteDto.getId());
            return true;
        }

        throw new CustomException(CommentExceptionCode.UNAUTHORIZED);
    }

    /**
     * id로 댓글 조회
     */
    private Comment findCommentById(int id) {
        // 옵셔널이 비었을 경우는 sql 오류 말고 가능성이 없고, 트랜잭션이 걸려 있으니
        // isPresent 확인 안 함
        return commentRepository.findCommentById(id).get();
    }

}

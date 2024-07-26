package com.jj.hello_blog.domain.comment.service;

import com.jj.hello_blog.domain.comment.dto.Comment;
import com.jj.hello_blog.domain.comment.dto.CommentResponse;
import com.jj.hello_blog.domain.comment.dto.CommentSaveDto;
import com.jj.hello_blog.domain.comment.dto.CommentUpdateDto;
import com.jj.hello_blog.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponse saveComment(CommentSaveDto commentSaveDto) {
        Comment comment = new Comment(
                null, commentSaveDto.getContent(), null,
                commentSaveDto.getMember_id(), commentSaveDto.getPostId(), commentSaveDto.getParentId());

        // 저장
        commentRepository.saveComment(comment);

        // createdAt 받아오기
        comment = findCommentById(comment.getId());

        return new CommentResponse(
                comment.getId(), comment.getContent(), comment.getCreatedAt(),
                comment.getMemberId(), comment.getPostId(), comment.getParentId(),
                commentSaveDto.getEmail(), 0);
    }

    public boolean updateComment(CommentUpdateDto commentUpdateDto) {
        commentRepository.updateComment(commentUpdateDto);
        return true;
    }

    public boolean deleteComment(int id) {
        commentRepository.deleteComment(id);
        return true;
    }

    private Comment findCommentById(int id) {
        // 옵셔널이 비었을 경우는 sql 오류 말고 가능성이 없고, 트랜잭션이 걸려 있으니
        // isPresent 확인 안 함
        return commentRepository.findCommentById(id).get();
    }

}

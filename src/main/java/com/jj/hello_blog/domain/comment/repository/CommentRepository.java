package com.jj.hello_blog.domain.comment.repository;

import com.jj.hello_blog.domain.comment.dto.Comment;

import com.jj.hello_blog.domain.comment.dto.CommentUpdateDto;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final CommentMapper commentMapper;

    public void saveComment(Comment comment) {
        commentMapper.saveComment(comment);
    }

    public void updateComment(CommentUpdateDto commentUpdateDto) {
        commentMapper.updateComment(commentUpdateDto);
    }

    public void deleteComment(int id) {
        commentMapper.deleteComment(id);
    }

    public Optional<Comment> findCommentById(int id) {
        return commentMapper.findCommentById(id);
    }

}

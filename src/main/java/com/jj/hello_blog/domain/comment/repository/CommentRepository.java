package com.jj.hello_blog.domain.comment.repository;

import com.jj.hello_blog.domain.comment.dto.Comment;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepository {

    private final CommentMapper commentMapper;

    public void saveComment(Comment comment) {
        commentMapper.saveComment(comment);
    }

}

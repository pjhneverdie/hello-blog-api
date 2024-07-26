package com.jj.hello_blog.domain.comment.repository;

import com.jj.hello_blog.domain.comment.dto.Comment;
import com.jj.hello_blog.domain.comment.dto.CommentUpdateDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Optional;

@Mapper
public interface CommentMapper {

    void saveComment(Comment comment);

    void updateComment(CommentUpdateDto commentUpdateDto);

    void deleteComment(int id);

    Optional<Comment> findCommentById(int id);

}

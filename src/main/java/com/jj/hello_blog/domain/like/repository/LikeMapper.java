package com.jj.hello_blog.domain.like.repository;

import com.jj.hello_blog.domain.like.dto.LikeCommentDto;
import com.jj.hello_blog.domain.like.dto.LikePostDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeMapper {

    void savePostLike(LikePostDto like);

    void saveCommentLike(LikeCommentDto like);

    int countLikeById(Integer postId, Integer commentId);

}

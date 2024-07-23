package com.jj.hello_blog.domain.like.repository;

import org.apache.ibatis.annotations.Mapper;

import com.jj.hello_blog.domain.like.dto.Like;

@Mapper
public interface LikeMapper {

    void saveLike(Like like);

    int countLikeById(int postId, Integer commentId);

}

package com.jj.hello_blog.domain.like.repository;

import com.jj.hello_blog.domain.like.entity.Like;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LikeMapper {
    void like(Like like);

    int findTotalCount(int postId, Integer commentId);
}

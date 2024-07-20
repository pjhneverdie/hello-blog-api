package com.jj.hello_blog.domain.like.repository;

import com.jj.hello_blog.domain.like.entity.Like;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeRepository {

    private final LikeMapper likeMapper;

    public Like like(Like like) {
        likeMapper.like(like);
        return like;
    }

    public int findTotalCount(int postId, Integer commentId) {
        return likeMapper.findTotalCount(postId, commentId);
    }

}

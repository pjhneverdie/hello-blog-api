package com.jj.hello_blog.domain.like.repository;

import com.jj.hello_blog.domain.like.dto.Like;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeRepository {

    private final LikeMapper likeMapper;

    public void saveLike(Like like) {
        likeMapper.saveLike(like);
    }

    public int countLikeById(int postId, Integer commentId) {
        return likeMapper.countLikeById(postId, commentId);
    }

}

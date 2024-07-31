package com.jj.hello_blog.domain.like.repository;

import com.jj.hello_blog.domain.like.dto.LikeCommentDto;
import com.jj.hello_blog.domain.like.dto.LikePostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class LikeRepository {

    private final LikeMapper likeMapper;

    public void savePostLike(LikePostDto like) {
        likeMapper.savePostLike(like);
    }

    public void saveCommentLike(LikeCommentDto like) {
        likeMapper.saveCommentLike(like);
    }

    public int countLikeById(Integer postId, Integer commentId) {
        return likeMapper.countLikeById(postId, commentId);
    }

}

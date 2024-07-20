package com.jj.hello_blog.domain.like.service;

import com.jj.hello_blog.domain.like.entity.Like;
import com.jj.hello_blog.domain.like.repository.LikeRepository;
import com.jj.hello_blog.web.like.form.LikeLikeForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;

    public int like(LikeLikeForm likeLikeForm) {
        Like like = new Like(null, likeLikeForm.getMemberId(), likeLikeForm.getPostId());

        Like liked = likeRepository.like(like);

        return findTotalCount(liked.getPostId());
    }

    private int findTotalCount(int postId) {
        return likeRepository.findTotalCount(postId, null);
    }

    private int findTotalCount(int postId, int commentId) {
        return likeRepository.findTotalCount(postId, commentId);
    }

}

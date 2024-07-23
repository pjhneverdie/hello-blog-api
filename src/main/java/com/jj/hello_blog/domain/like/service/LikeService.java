package com.jj.hello_blog.domain.like.service;

import com.jj.hello_blog.domain.like.dto.Like;
import com.jj.hello_blog.domain.like.dto.LikeSaveDto;
import com.jj.hello_blog.domain.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    /**
     * saveLike, 게시글 or 댓글 좋아요 로직
     *
     * @return 좋아요를 누른 게시글 or 댓글의 총 좋아요 수 반환
     */
    public int saveLike(LikeSaveDto likeSaveDto) {
        Like like = new Like(null, likeSaveDto.getMemberId(), likeSaveDto.getPostId(), likeSaveDto.getCommentId());

        likeRepository.saveLike(like);

        // 게시글 총 좋아요 수 확인
        if (like.getCommentId() == null) {
            return countLikeById(like.getPostId());
        }

        // 댓글
        return countLikeById(like.getPostId(), like.getCommentId());
    }

    /**
     * countLikeById, 게시글의 총 좋아요 수 조회
     *
     * @return 게시글의 총 좋아요 수 반환
     */
    private int countLikeById(int postId) {
        return likeRepository.countLikeById(postId, null);
    }

    /**
     * countLikeById, 댓글의 총 좋아요 수 조회
     *
     * @return 댓글의 총 좋아요 수 반환
     */
    private int countLikeById(int postId, int commentId) {
        return likeRepository.countLikeById(postId, commentId);
    }

}

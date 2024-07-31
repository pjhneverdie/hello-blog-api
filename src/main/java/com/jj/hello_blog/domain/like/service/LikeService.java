package com.jj.hello_blog.domain.like.service;

import com.jj.hello_blog.domain.common.exception.CustomException;
import com.jj.hello_blog.domain.like.dto.LikeCommentDto;
import com.jj.hello_blog.domain.like.dto.LikePostDto;
import com.jj.hello_blog.domain.like.exception.LikeExceptionCode;
import com.jj.hello_blog.domain.like.repository.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;

    /**
     * likePost, 게시글 좋아요 로직
     */
    @Transactional
    public int likePost(LikePostDto likePostDto) {
        try {
            likeRepository.savePostLike(likePostDto);
            return countLikeById(likePostDto.getPostId(), null);
        } catch (DuplicateKeyException e) {
            // 좋아요는 한 번만 허용
            throw new CustomException(LikeExceptionCode.DUPLICATED_LIKE);
        }
    }

    /**
     * likeComment, 댓글 좋아요 로직
     */
    @Transactional
    public int likeComment(LikeCommentDto likeCommentDto) {
        try {
            likeRepository.saveCommentLike(likeCommentDto);
            return countLikeById(null, likeCommentDto.getCommentId());
        } catch (DuplicateKeyException e) {
            // 좋아요는 한 번만 허용
            throw new CustomException(LikeExceptionCode.DUPLICATED_LIKE);
        }
    }

    /**
     * countLikeById, 게시글 or 댓글의 총 좋아요 수 조회
     */
    private int countLikeById(Integer postId, Integer commentId) {
        return likeRepository.countLikeById(postId, commentId);
    }

}

package com.jj.hello_blog.web.like.controller;

import com.jj.hello_blog.domain.like.dto.LikeSaveDto;
import com.jj.hello_blog.domain.like.service.LikeService;
import com.jj.hello_blog.web.like.form.LikeSaveForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/like")
@RestController
@RequiredArgsConstructor
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    int like(@RequestBody LikeSaveForm likeSaveForm) {
        return likeService.saveLike(new LikeSaveDto(likeSaveForm.getMemberId(), likeSaveForm.getPostId(), likeSaveForm.getCommentId()));
    }

}

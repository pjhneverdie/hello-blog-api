package com.jj.hello_blog.web.like.controller;

import com.jj.hello_blog.domain.like.service.LikeService;
import com.jj.hello_blog.web.like.form.LikeLikeForm;
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

    @PostMapping("/like")
    int like(@Valid @RequestBody LikeLikeForm likeLikeForm) {
        return likeService.like(likeLikeForm);
    }

}

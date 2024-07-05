package com.jj.hello_blog.web.member.controller;

import com.jj.hello_blog.domain.member.entity.Member;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/member")
public class MemberController {

    public void signUp(@RequestBody Member member) {
    }

}

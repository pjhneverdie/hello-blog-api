package com.jj.hello_blog.web.member.controller;

import com.jj.hello_blog.domain.member.entity.Member;
import com.jj.hello_blog.domain.member.service.MemberService;
import com.jj.hello_blog.web.member.form.MemberForm;
import com.jj.hello_blog.web.member.form.group.save.MemberSaveGroup;
import com.jj.hello_blog.web.member.form.group.save.MemberSaveGroupSequence;
import com.jj.hello_blog.web.session.SessionConst;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/member")
@RestController
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/signUp")
    public Member signUp(@Validated(MemberSaveGroupSequence.class) @RequestBody MemberForm memberForm, HttpSession session) {
        Member member = memberService.signUp(memberForm);
        session.setAttribute(SessionConst.MEMBER_KEY, member);
        return member;
    }

}

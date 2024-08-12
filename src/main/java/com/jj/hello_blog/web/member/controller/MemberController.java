package com.jj.hello_blog.web.member.controller;

import com.jj.hello_blog.domain.member.dto.Member;
import com.jj.hello_blog.domain.member.dto.MemberSignInDto;
import com.jj.hello_blog.domain.member.dto.MemberSignUpDto;
import com.jj.hello_blog.domain.member.service.MemberService;
import com.jj.hello_blog.domain.member.dto.MemberResponse;
import com.jj.hello_blog.web.common.response.ApiResponse;
import com.jj.hello_blog.web.member.form.MemberSignInForm;
import com.jj.hello_blog.web.member.form.MemberSignUpForm;
import com.jj.hello_blog.web.common.session.SessionConst;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/member")
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<MemberResponse>> me(HttpSession session) {
        Member member = (Member) session.getAttribute(SessionConst.MEMBER_KEY);
        return ResponseEntity.ok(new ApiResponse<>(new MemberResponse(member.getEmail())));
    }

    @PostMapping("/signIn")
    public ResponseEntity<ApiResponse<MemberResponse>> signIn(@Valid @RequestBody MemberSignInForm memberSignInForm, HttpSession session) {
        Member member = memberService.signIn(new MemberSignInDto(memberSignInForm.getEmail(), memberSignInForm.getPassword()));

        session.setAttribute(SessionConst.MEMBER_KEY, member);

        return ResponseEntity.ok(new ApiResponse<>(new MemberResponse(member.getEmail())));
    }

    @PostMapping("/signUp")
    public ResponseEntity<ApiResponse<MemberResponse>> signUp(@Valid @RequestBody MemberSignUpForm memberSignUpForm, HttpSession session) {
        Member member = memberService.signUp(new MemberSignUpDto(memberSignUpForm.getEmail(), memberSignUpForm.getPassword()));

        session.setAttribute(SessionConst.MEMBER_KEY, member);

        return ResponseEntity.ok(new ApiResponse<>(new MemberResponse(member.getEmail())));
    }

    @GetMapping("/signOut")
    public ResponseEntity<ApiResponse<Boolean>> signOut(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok(new ApiResponse<>(true));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Boolean>> deleteMember(HttpSession session, @PathVariable int id) {
        session.invalidate();
        return ResponseEntity.ok(new ApiResponse<>(memberService.deleteMember(id)));
    }

}

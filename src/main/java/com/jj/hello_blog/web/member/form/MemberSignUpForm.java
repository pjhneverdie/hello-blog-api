package com.jj.hello_blog.web.member.form;

import lombok.Getter;

@Getter
public class MemberSignUpForm extends MemberSignInForm {
    public MemberSignUpForm(String email, String password) {
        super(email, password);
    }
}

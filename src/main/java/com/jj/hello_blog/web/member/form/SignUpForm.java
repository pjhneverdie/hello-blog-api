package com.jj.hello_blog.web.member.form;

import lombok.Getter;

@Getter
public class SignUpForm extends SignInForm {
    public SignUpForm(String email, String password) {
        super(email, password);
    }
}

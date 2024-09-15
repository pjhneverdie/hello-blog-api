package com.jj.hello_blog.web.member.form;

import lombok.Getter;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@Getter
public class MemberSignInForm {

    @Email
    @NotNull
    private final String email;

    @Size(min = 6)
    @NotBlank()
    @NotNull()
    private final String password;

    public MemberSignInForm(String email, String password) {
        this.email = email;
        this.password = password;
    }

}

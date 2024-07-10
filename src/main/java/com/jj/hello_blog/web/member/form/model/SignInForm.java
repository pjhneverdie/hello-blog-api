package com.jj.hello_blog.web.member.form.model;

import lombok.Getter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
public class SignInForm {

    @Email
    @NotNull
    private final String email;

    @Size(min = 6)
    @NotBlank()
    @NotNull()
    private final String password;

    public SignInForm(String email, String password) {
        this.email = email;
        this.password = password;
    }

}

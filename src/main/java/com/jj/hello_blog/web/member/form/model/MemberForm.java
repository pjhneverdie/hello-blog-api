package com.jj.hello_blog.web.member.form.model;

import com.jj.hello_blog.domain.member.entity.Member;
import com.jj.hello_blog.web.member.form.group.save.MemberSaveGroup;
import com.jj.hello_blog.web.member.form.group.update.MemberUpdateGroup;

import lombok.Getter;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

@Getter
public class MemberForm {
    @NotNull(message = "id는 필수입니다.", groups = MemberUpdateGroup.class)
    private final Integer id;

    @Email(message = "올바른 이메일을 입력해 주세요.", groups = MemberSaveGroup.Email.class)
    @NotNull(message = "이메일은 필수입니다.", groups = MemberSaveGroup.NotNull.class)
    private final String email;

    @Size(message = "비밀번호는 공백 제외 최소 6자 이상이어야 합니다.", min = 6, groups = MemberSaveGroup.Size.class)
    @NotBlank(message = "비밀번호는 공백 제외 최소 6자 이상이어야 합니다.", groups = MemberSaveGroup.NotBlank.class)
    @NotNull(message = "비밀번호는 필수입니다.", groups = MemberSaveGroup.NotNull.class)
    private final String password;

    public MemberForm(Integer id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public Member toMember() {
        return new Member(this.id, this.email, this.password);
    }
}

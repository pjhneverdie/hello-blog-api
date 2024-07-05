package com.jj.hello_blog.web.member.form;


import com.jj.hello_blog.domain.member.entity.Member;
import com.jj.hello_blog.web.member.form.group.save.MemberSaveGroup;
import com.jj.hello_blog.web.member.form.group.update.MemberUpdateGroup;
import lombok.Getter;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
public class MemberForm {
    @NotNull(message = "id는 필수입니다.", groups = MemberUpdateGroup.class)
    private final Integer id;

    @Size(min = 2, max = 15, message = "이름은 2자 이상 16자 미만으로 설정해 주세요.", groups = MemberSaveGroup.Size.class)
    @NotBlank(message = "공백은 이름으로 사용할 수 없습니다.", groups = MemberSaveGroup.NotBlank.class)
    @NotNull(message = "이름은 필수입니다.", groups = MemberSaveGroup.NotNull.class)
    private final String name;

    @Email(message = "올바른 이메일을 입력해 주세요.", groups = MemberSaveGroup.Email.class)
    @NotNull(message = "이메일은 필수입니다.", groups = MemberSaveGroup.NotNull.class)
    private final String email;

    public MemberForm(Integer id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Member toMember() {
        return new Member(id, name, email);
    }
}

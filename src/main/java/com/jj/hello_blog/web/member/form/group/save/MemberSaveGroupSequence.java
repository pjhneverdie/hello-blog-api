package com.jj.hello_blog.web.member.form.group.save;

import jakarta.validation.GroupSequence;

@GroupSequence({MemberSaveGroup.NotNull.class, MemberSaveGroup.NotBlank.class, MemberSaveGroup.Size.class, MemberSaveGroup.Email.class})
public interface MemberSaveGroupSequence {
}

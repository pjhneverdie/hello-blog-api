package com.jj.hello_blog.web.member.form.group.update;

import com.jj.hello_blog.web.member.form.group.save.MemberSaveGroup;

import jakarta.validation.GroupSequence;

@GroupSequence({MemberUpdateGroup.class, MemberSaveGroup.class})
public interface MemberUpdateGroupSequence {
}

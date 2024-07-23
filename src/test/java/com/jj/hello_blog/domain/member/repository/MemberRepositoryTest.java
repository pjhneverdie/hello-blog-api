package com.jj.hello_blog.domain.member.repository;

import com.jj.hello_blog.domain.member.dto.Member;
import jakarta.validation.constraints.AssertTrue;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void signUp() {
        // Given
        Member member = getMember();

        // When
        memberRepository.signUp(member);

        // Then
        Assertions.assertThat(member.getId()).isNotNull();
    }

    @Test
    void findByEmail() {
        // Given
        Member member = getMember();
        memberRepository.signUp(member);

        // When
        Optional<Member> existMember = memberRepository.findByEmail("test@test.com");

        // Then
        Assertions.assertThat(existMember.get().getId().equals(member.getId())).isTrue();
    }

    public static Member getMember() {
        return new Member(null, "test@test.com", "123456");
    }
}
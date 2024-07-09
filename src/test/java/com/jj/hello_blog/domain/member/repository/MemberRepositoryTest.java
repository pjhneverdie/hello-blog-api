package com.jj.hello_blog.domain.member.repository;

import com.jj.hello_blog.domain.member.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    void signUp() {
        // Given
        Member memberYet = new Member(null, "test@test.com", "123456");

        // When
        Member member = memberRepository.signUp(memberYet);

        // Then
        Assertions.assertThat(member.getId()).isNotNull();
    }

    @Test
    void findByEmail() {
        // Given
        signUp();

        // When
        Optional<Member> member = memberRepository.findByEmail("test@test.com");
        Optional<Member> newMember = memberRepository.findByEmail("new@test.com");

        // Then
        Assertions.assertThat(member).isNotEmpty();
        Assertions.assertThat(newMember).isEmpty();
    }

}
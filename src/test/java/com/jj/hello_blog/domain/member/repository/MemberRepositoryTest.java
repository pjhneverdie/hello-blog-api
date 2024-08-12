package com.jj.hello_blog.domain.member.repository;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jj.hello_blog.domain.member.dto.Member;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원가입 테스트")
    void insertMember() {
        // Given
        Member member = createMember();

        // When
        memberRepository.insertMember(member);

        // Then
        assertNotNull(member.getId());
    }

    @Test
    @DisplayName("회원탈퇴 테스트")
    void deleteMemberById() {
        // Given
        Member member = createMember();
        memberRepository.insertMember(member);

        // When
        memberRepository.deleteMemberById(member.getId());

        // Then
        Optional<Member> foundMember = memberRepository.selectMemberByEmail(member.getEmail());
        assertFalse(foundMember.isPresent());
    }


    @Test
    @DisplayName("이메일로 멤버 조회 테스트")
    void selectMemberByEmail() {
        // Given
        Member member = createMember();
        memberRepository.insertMember(member);

        // When
        Optional<Member> foundMember = memberRepository.selectMemberByEmail(member.getEmail());

        // Then
        assertEquals(foundMember.get().getId(), member.getId());
    }

    /**
     * createMember, 멤버 데이터 생성 유틸
     */
    public static Member createMember() {
        return new Member(null, "test@test.com", "123456");
    }

}
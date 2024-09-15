package com.jj.hello_blog;

import lombok.RequiredArgsConstructor;

import org.springframework.context.event.EventListener;

import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Configuration;

import org.springframework.beans.factory.annotation.Value;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import com.jj.hello_blog.domain.member.dto.Member;
import com.jj.hello_blog.domain.member.repository.MemberRepository;

@SpringBootApplication
@RequiredArgsConstructor
public class HelloBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloBlogApplication.class, args);
    }

    @Configuration
    @Profile("!test")
    public static class AdminInitializer {

        @Value("${blog.config.admin.email}")
        private String adminEmail;

        @Value("${blog.config.admin.password}")
        private String adminPassword;

        private final MemberRepository memberRepository;

        public AdminInitializer(MemberRepository memberRepository) {
            this.memberRepository = memberRepository;
        }

        @EventListener(ApplicationReadyEvent.class)
        private void adminRegistration() {
            if (memberRepository.selectMemberByEmail(adminEmail).isEmpty()) {
                memberRepository.insertMember(new Member(null, adminEmail, adminPassword));
            }
        }

    }

}
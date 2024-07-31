package com.jj.hello_blog.domain.comment.repository;

import com.jj.hello_blog.domain.category.dto.Category;
import com.jj.hello_blog.domain.category.repository.CategoryRepository;
import com.jj.hello_blog.domain.category.repository.CategoryRepositoryTest;
import com.jj.hello_blog.domain.member.dto.Member;
import com.jj.hello_blog.domain.member.repository.MemberRepository;
import com.jj.hello_blog.domain.member.repository.MemberRepositoryTest;
import com.jj.hello_blog.domain.post.dto.Post;
import com.jj.hello_blog.domain.post.repository.PostRepository;
import com.jj.hello_blog.domain.post.repository.PostRepositoryTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
public class CommentRepositoryTestSetUp {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostRepository postRepository;

    protected Member member;

    protected Category category;

    protected Post post;

    @BeforeEach
    @DisplayName("댓글 테스트 셋업")
    void setUp() {
        this.member = MemberRepositoryTest.getMember();
        memberRepository.saveMember(member);

        this.category = CategoryRepositoryTest.getCategory();
        categoryRepository.saveCategory(category);

        this.post = PostRepositoryTest.getPost(category);
        postRepository.savePost(this.post);
    }

    @Test
    void contextLoads() {
    }

}
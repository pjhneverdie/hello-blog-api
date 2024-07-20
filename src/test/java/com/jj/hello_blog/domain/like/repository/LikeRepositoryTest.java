package com.jj.hello_blog.domain.like.repository;

import com.jj.hello_blog.domain.category.entity.Category;
import com.jj.hello_blog.domain.category.repository.CategoryRepository;
import com.jj.hello_blog.domain.category.repository.CategoryRepositoryTest;
import com.jj.hello_blog.domain.like.entity.Like;
import com.jj.hello_blog.domain.member.entity.Member;
import com.jj.hello_blog.domain.member.repository.MemberRepository;
import com.jj.hello_blog.domain.member.repository.MemberRepositoryTest;
import com.jj.hello_blog.domain.post.entity.Post;
import com.jj.hello_blog.domain.post.repository.PostRepository;
import com.jj.hello_blog.domain.post.repository.PostRepositoryTest;
import jakarta.validation.constraints.AssertTrue;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class LikeRepositoryTest {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostRepository postRepository;

    private Member member;

    private Post post;

    // like 레포지토리 테스트를 위한 베이스 작업
    @BeforeEach
    void setUp() {
        this.member = memberRepository.signUp(MemberRepositoryTest.getMemberYet());

        Category category = CategoryRepositoryTest.getRoot();
        categoryRepository.addCategory(category);

        this.post = postRepository.post(PostRepositoryTest.getPost(category));
    }

    @Test
    void like() {
        // Given
        Like like = getLike();

        // When
        Like liked = likeRepository.like(like);

        // Then
        assertNotNull(liked.getId());
    }

    @Test
    void findTotalCount() {
        // Given
        int totalCount1 = likeRepository.findTotalCount(post.getId(), null);

        // When
        like();

        // Then
        int totalCount2 = likeRepository.findTotalCount(post.getId(), null);
        Assertions.assertThat(totalCount1 < totalCount2).isTrue();
    }

    private Like getLike() {
        return new Like(null, member.getId(), post.getId());
    }
}
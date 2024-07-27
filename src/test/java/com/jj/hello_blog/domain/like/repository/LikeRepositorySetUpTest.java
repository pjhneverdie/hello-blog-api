package com.jj.hello_blog.domain.like.repository;

import com.jj.hello_blog.domain.category.dto.Category;
import com.jj.hello_blog.domain.category.repository.CategoryRepository;
import com.jj.hello_blog.domain.category.repository.CategoryRepositoryTest;
import com.jj.hello_blog.domain.comment.dto.Comment;
import com.jj.hello_blog.domain.comment.repository.CommentRepository;
import com.jj.hello_blog.domain.comment.repository.CommentRepositoryTest;
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

@SpringBootTest
public class LikeRepositorySetUpTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentRepository commentRepository;

    protected Member member;

    protected Category category;

    protected Post post;

    protected Comment comment;

    @BeforeEach
    @DisplayName("좋아요를 누를 게시글, 댓글 셋업")
    void setUp() {
        this.member = MemberRepositoryTest.getMember();
        memberRepository.saveMember(member);

        this.category = CategoryRepositoryTest.getCategory();
        categoryRepository.saveCategory(category);

        this.post = PostRepositoryTest.getPost(category);
        postRepository.savePost(this.post);

        this.comment = CommentRepositoryTest.getComment(this.member, this.post);
        commentRepository.saveComment(this.comment);
    }

    @Test
    void contextLoads() {
    }

}

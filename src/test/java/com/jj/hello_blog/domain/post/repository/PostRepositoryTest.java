package com.jj.hello_blog.domain.post.repository;

import com.jj.hello_blog.domain.category.dto.Category;
import com.jj.hello_blog.domain.category.repository.CategoryRepository;
import com.jj.hello_blog.domain.category.repository.CategoryRepositoryTest;
import com.jj.hello_blog.domain.post.dto.Post;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@Transactional
@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    /**
     * 카테고리가 있어야 글을 쓸 수 있음
     */
    @Autowired
    private CategoryRepository categoryRepository;
    private Category category;

    @BeforeEach
    @DisplayName("게시글을 작성할 카테고리 셋업")
    void setUp() {
        category = CategoryRepositoryTest.getCategory();
        categoryRepository.addCategory(category);
    }

    @Test
    @DisplayName("게시글 작성 테스트")
    void save() {
        // Given
        Post post = getPost(category);

        // When
        postRepository.save(post);

        // Then
        assertNotNull(post.getId());
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    void updateSave() {
        // Given
        Post post = getPost(category);
        postRepository.save(post);

        Post newPost = new Post(
                post.getId(), post.getTitle() + "update", post.getContent() + "update",
                null, null, post.getCategoryId());

        // When
        postRepository.updatePost(newPost);

        // Then
        Assertions.assertThat(post.getId()).isEqualTo(newPost.getId());
        Assertions.assertThat((post.getTitle()).equals(newPost.getTitle())).isFalse();
        Assertions.assertThat((post.getContent()).equals(newPost.getContent())).isFalse();
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    void deleteSaveById() {
        // Given
        Post post = getPost(category);
        postRepository.save(post);

        // When
        postRepository.deletePostById(post.getId());

        // Then
        Optional<Post> optionalPost = postRepository.findPostById(post.getId());
        Assertions.assertThat(optionalPost.isPresent()).isFalse();
    }

    @Test
    @DisplayName("id로 게시글 단건 조회 테스트")
    void findSaveById() {
        // Given
        Post post = getPost(category);
        postRepository.save(post);

        // When
        Optional<Post> optionalPost = postRepository.findPostById(post.getId());

        // Then
        Assertions.assertThat(optionalPost.isPresent()).isTrue();
    }

    @Test
    @DisplayName("카테고리에 속한 게시글 수 조회 테스트")
    void countSaveByCategoryId() {
        // Given
        Post post = getPost(category);
        postRepository.save(post);

        // When
        int postCount = postRepository.countPostByCategoryId(category.getId());

        // Then
        assertEquals(1, postCount);
    }

    /**
     * 테스트용 게시글 생성 유틸
     */
    public static Post getPost(Category category) {
        return new Post(null, "test", "test", null, null, category.getId());
    }

}
package com.jj.hello_blog.domain.post.repository;

import com.jj.hello_blog.domain.category.dto.Category;
import com.jj.hello_blog.domain.post.dto.Post;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
public class PostRepositoryTest extends PostRepositoryTestSetUp {

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("게시글 작성 테스트")
    void savePost() {
        // Given
        Post post = getPost(category);

        // When
        postRepository.savePost(post);

        // Then
        assertNotNull(post.getId());
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    void updateSavePost() {
        // Given
        Post post = getPost(category);
        postRepository.savePost(post);

        Post newPost = new Post(
                post.getId(), post.getTitle() + "update", post.getContent() + "update",
                null, null, post.getCategoryId());

        // When
        postRepository.updatePost(newPost);

        // Then
        assertEquals(post.getId(), newPost.getId());
        assertNotEquals(post.getTitle(), newPost.getTitle());
        assertNotEquals(post.getContent(), newPost.getContent());
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    void deleteSavePostById() {
        // Given
        Post post = getPost(category);
        postRepository.savePost(post);

        // When
        postRepository.deletePostById(post.getId());

        // Then
        Optional<Post> optionalPost = postRepository.findPostById(post.getId());
        assertFalse(optionalPost.isPresent());
    }

    @Test
    @DisplayName("id로 게시글 단건 조회 테스트")
    void findSavePostById() {
        // Given
        Post post = getPost(category);
        postRepository.savePost(post);

        // When
        Optional<Post> optionalPost = postRepository.findPostById(post.getId());

        // Then
        assertTrue(optionalPost.isPresent());
    }

    @Test
    @DisplayName("카테고리에 속한 게시글 수 조회 테스트")
    void countSavePostByCategoryId() {
        // Given
        Post post = getPost(category);
        postRepository.savePost(post);

        // When
        int postCount = postRepository.countPostByCategoryId(category.getId());

        // Then
        assertEquals(1, postCount);
    }

    /**
     * getPost, 게시글 데이터 생성 유틸
     */
    public static Post getPost(Category category) {
        return new Post(null, "test", "test", null, null, category.getId());
    }

}
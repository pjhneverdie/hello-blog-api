package com.jj.hello_blog.domain.post.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.jj.hello_blog.domain.post.dto.*;
import com.jj.hello_blog.domain.category.dto.Category;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
public class PostRepositoryTest extends PostRepositoryTestSetUp {

    @Autowired
    private PostRepository postRepository;

    @Test
    @DisplayName("게시글 작성 테스트")
    void insertPost() {
        // Given
        Post post = createPost(category);

        // When
        postRepository.insertPost(post);

        // Then
        assertNotNull(post.getId());
    }

    @Test
    @DisplayName("id로 게시글 조회 테스트")
    void selectPostById() {
        // Given
        Post post = createPost(category);
        postRepository.insertPost(post);

        // When
        Optional<Post> selectedPost = postRepository.selectPostById(post.getId());

        // Then
        assertTrue(selectedPost.isPresent());
    }

    @Test
    @DisplayName("최근 게시글 페이지네이션 테스트")
    void selectPostsOrderByCreatedAtDesc() {
        // Given
        for (int i = 0; i < 15; i++) {
            Post post = createPost(category);
            postRepository.insertPost(post);
        }

        PostPaginationCond postPaginationCond1 = new PostPaginationCond(1);
        PostPaginationCond postPaginationCond2 = new PostPaginationCond(2);

        // When
        List<PostResponse> posts1 = postRepository.selectPostsOrderByCreatedAtDesc(postPaginationCond1);
        List<PostResponse> posts2 = postRepository.selectPostsOrderByCreatedAtDesc(postPaginationCond2);

        // Then
        assertEquals(15, posts1.size());
        assertEquals(0, posts2.size());

        for (int i = 0; i < posts1.size() - 1; i++) {
            assertTrue(posts1.get(i).getCreatedAt().isAfter(posts1.get(i + 1).getCreatedAt()) ||
                    posts1.get(i).getCreatedAt().isEqual(posts1.get(i + 1).getCreatedAt()));
        }
    }

    @Test
    @DisplayName("카테고리의 게시글 페이지네이션 테스트")
    void selectPostsByCategoryIdOrderByCreatedAtDesc() {
        // Given
        for (int i = 0; i < 15; i++) {
            Post post = createPost(category);
            postRepository.insertPost(post);
        }

        PostPaginationCond postPaginationCond1 = new PostPaginationCond(1);
        PostPaginationCond postPaginationCond2 = new PostPaginationCond(2);

        // When
        List<PostResponse> posts1 = postRepository.selectPostsByCategoryIdOrderByCreatedAtDesc(category.getId(), postPaginationCond1);
        List<PostResponse> posts2 = postRepository.selectPostsByCategoryIdOrderByCreatedAtDesc(category.getId(), postPaginationCond2);

        // Then
        assertEquals(15, posts1.size());
        assertEquals(0, posts2.size());

        for (int i = 0; i < posts1.size() - 1; i++) {
            assertTrue(posts1.get(i).getCreatedAt().isAfter(posts1.get(i + 1).getCreatedAt()) ||
                            posts1.get(i).getCreatedAt().isEqual(posts1.get(i + 1).getCreatedAt()));
        }
    }

    @Test
    @DisplayName("게시글 수정 테스트")
    void updatePostById() {
        // Given
        Post post = createPost(category);
        postRepository.insertPost(post);

        PostUpdateQueryDto postUpdateDto = new PostUpdateQueryDto(post.getId(), post.getTitle() + "update", post.getContent() + "update", post.getThumbUrl(), post.getCategoryId());

        // When
        postRepository.updatePostById(postUpdateDto);

        // Then
        Optional<Post> updatedPost = postRepository.selectPostById(post.getId());
        assertTrue(updatedPost.isPresent());
        assertEquals(updatedPost.get().getId(), postUpdateDto.getId());
        assertEquals(updatedPost.get().getTitle(), postUpdateDto.getTitle());
        assertEquals(updatedPost.get().getContent(), postUpdateDto.getContent());
    }

    @Test
    @DisplayName("게시글 삭제 테스트")
    void deletePostById() {
        // Given
        Post post = createPost(category);
        postRepository.insertPost(post);

        // When
        postRepository.deletePostById(post.getId());

        // Then
        Optional<Post> optionalPost = postRepository.selectPostById(post.getId());
        assertFalse(optionalPost.isPresent());
    }

    /**
     * createPost, 게시글 데이터 생성 유틸
     */
    public static Post createPost(Category category) {
        return new Post(null, "test", "test", "test", category.getId(), null, null);
    }

}
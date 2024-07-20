package com.jj.hello_blog.domain.post.repository;

import com.jj.hello_blog.domain.category.entity.Category;
import com.jj.hello_blog.domain.category.repository.CategoryRepository;
import com.jj.hello_blog.domain.category.repository.CategoryRepositoryTest;
import com.jj.hello_blog.domain.post.entity.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@SpringBootTest
public class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    private Category category;

    @BeforeEach
    void setUp() {
        category = CategoryRepositoryTest.getRoot();
        categoryRepository.addCategory(category);
    }

    @Test
    void post() {
        // Given
        Post post = getPost(category);

        // When
        postRepository.post(post);

        // Then
        Assertions.assertThat(post.getId()).isNotNull();
    }

    @Test
    void findById() {
        // Given
        Post post = getPost(category);
        postRepository.post(post);

        // When
        Optional<Post> optionalPost = postRepository.findById(post.getId());

        // Then
        Assertions.assertThat(optionalPost.isPresent()).isTrue();
    }

    public static Post getPost(Category category) {
        return new Post(null, "test", "test", null, null, category.getId());
    }

}
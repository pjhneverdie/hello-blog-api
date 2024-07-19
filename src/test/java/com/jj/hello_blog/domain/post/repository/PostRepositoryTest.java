package com.jj.hello_blog.domain.post.repository;

import com.jj.hello_blog.domain.category.entity.Category;
import com.jj.hello_blog.domain.category.repository.CategoryRepository;
import com.jj.hello_blog.domain.category.repository.CategoryRepositoryTest;
import com.jj.hello_blog.domain.post.entity.Post;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Transactional
@SpringBootTest
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void post() {
        // Given
        Category category = CategoryRepositoryTest.getRoot();
        categoryRepository.addCategory(category);

        Post post = getPost(category);

        // When
        postRepository.post(post);

        // Then
        Assertions.assertThat(post.getId()).isNotNull();
    }

    @Test
    void findPostById() {
        // Given
        Category category = CategoryRepositoryTest.getRoot();
        categoryRepository.addCategory(category);

        Post post = getPost(category);

        postRepository.post(post);

        // When
        Optional<Post> optionalPost = postRepository.findPostById(post.getId());

        // Then
        Assertions.assertThat(optionalPost.isPresent()).isTrue();
    }

    private Post getPost(Category category) {
        return new Post(null, "test", "test", null, null, category.getId());
    }

}
package com.jj.hello_blog.domain.post.service;

import com.jj.hello_blog.domain.category.repository.CategoryRepository;
import com.jj.hello_blog.domain.post.dto.Post;
import com.jj.hello_blog.domain.post.dto.PostResponse;
import com.jj.hello_blog.domain.post.dto.PostSaveDto;
import com.jj.hello_blog.domain.post.dto.PostUpdateDto;
import com.jj.hello_blog.domain.post.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public PostResponse save(PostSaveDto postSaveDto) {
        Post post = new Post(null, postSaveDto.getTitle(),
                postSaveDto.getContent(), null, null, postSaveDto.getCategoryId());

        postRepository.save(post);

        // createdAt, fixedAt 받아오기
        post = findPostById(post.getId());

        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getFixedAt(),
                post.getCategoryId(),
                0,
                0);
    }

    @Transactional
    public PostResponse updatePost(PostUpdateDto postUpdateDto) {
        Post post = new Post(postUpdateDto.getId(), postUpdateDto.getTitle(),
                postUpdateDto.getContent(), null, null, postUpdateDto.getCategoryId());

        postRepository.updatePost(post);

        // createdAt, fixedAt 받아오기
        post = findPostById(post.getId());

        return new PostResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCreatedAt(),
                post.getFixedAt(),
                post.getCategoryId(),
                0,
                0);
    }

    @Transactional
    public boolean deletePostById(int id) {
        int categoryId = findPostById(id).getCategoryId();

        postRepository.deletePostById(id);

        // 해당 게시글이, 게시글이 속한 카테고리의 마지막 글이면 게시글도 삭제
        if (countPostByCategoryId(categoryId) == 0) {
            categoryRepository.deleteCategoryById(categoryId);
        }

        return true;
    }

    private Post findPostById(int id) {
        Optional<Post> posted = postRepository.findPostById(id);

        // 옵셔널이 비었을 경우는 sql 오류 말고 가능성이 없고, 트랜잭션이 걸려 있으니
        // isPresent 확인 안 함
        return posted.get();
    }

    private int countPostByCategoryId(int categoryId) {
        return postRepository.countPostByCategoryId(categoryId);
    }

}

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

    /**
     * savePost, 게시글 작성
     */
    @Transactional
    public PostResponse savePost(PostSaveDto postSaveDto) {
        Post post = new Post(null, postSaveDto.getTitle(),
                postSaveDto.getContent(), null, null, postSaveDto.getCategoryId());

        postRepository.insertPost(post);

        // createdAt, fixedAt 받아오기
        post = findPostById(post.getId());

        return new PostResponse(
                post.getId(),
                post.getTitle(), post.getContent(),
                post.getCreatedAt(), post.getFixedAt(),
                post.getCategoryId(),
                0, 0
        );
    }

    /**
     * updatePost, 게시글 수정
     */
    public boolean updatePost(PostUpdateDto postUpdateDto) {
        postRepository.updatePost(postUpdateDto);
        return true;
    }

    /**
     * deletePostById, 게시글 삭제
     */
    @Transactional
    public boolean deletePost(int id) {
        int categoryId = findPostById(id).getCategoryId();

        postRepository.deletePost(id);

        // 해당 게시글이, 게시글이 속한 카테고리의 마지막 글이면 게시글도 삭제
        if (countPostByCategoryId(categoryId) == 0) {
            categoryRepository.deleteCategoryById(categoryId);
        }

        return true;
    }

    /**
     * findPostById, id로 게시글 조회
     */
    private Post findPostById(int id) {
        Optional<Post> posted = postRepository.findPostById(id);

        // 옵셔널이 비었을 경우는 sql 오류 말고 가능성이 없고, 트랜잭션이 걸려 있으니
        // isPresent 확인 안 함
        return posted.get();
    }

    /**
     * countPostByCategoryId, 카테고리에 해당하는 게시글 총 수 조회
     */
    private int countPostByCategoryId(int categoryId) {
        return postRepository.countPostByCategoryId(categoryId);
    }

}

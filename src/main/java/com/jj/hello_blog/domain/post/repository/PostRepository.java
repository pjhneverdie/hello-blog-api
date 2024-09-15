package com.jj.hello_blog.domain.post.repository;

import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import com.jj.hello_blog.domain.post.dto.Post;
import com.jj.hello_blog.domain.post.dto.PostResponse;
import com.jj.hello_blog.domain.post.dto.PostUpdateQueryDto;
import com.jj.hello_blog.domain.post.dto.PostPaginationCond;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final PostMapper postMapper;

    public void insertPost(Post post) {
        postMapper.insertPost(post);
    }

    public Optional<Post> selectPostById(int id) {
        return Optional.ofNullable(postMapper.selectPostById(id));
    }

    public List<PostResponse> selectPostsOrderByCreatedAtDesc(PostPaginationCond postPaginationCond) {
        return postMapper.selectPostsOrderByCreatedAtDesc(postPaginationCond);
    }

    public List<PostResponse> selectPostsByCategoryIdOrderByCreatedAtDesc(int categoryId, PostPaginationCond postPaginationCond) {
        return postMapper.selectPostsByCategoryIdOrderByCreatedAtDesc(categoryId, postPaginationCond);
    }

    public void updatePostById(PostUpdateQueryDto postUpdateQueryDto) {
        postMapper.updatePostById(postUpdateQueryDto);
    }

    public void deletePostById(int id) {
        postMapper.deletePostById(id);
    }

}

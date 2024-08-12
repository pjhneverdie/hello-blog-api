package com.jj.hello_blog.domain.post.repository;


import com.jj.hello_blog.domain.post.dto.Post;
import com.jj.hello_blog.domain.post.dto.PostUpdateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final PostMapper postMapper;

    public void insertPost(Post post) {
        postMapper.savePost(post);
    }

    public void updatePost(PostUpdateDto postUpdateDto) {
        postMapper.updatePost(postUpdateDto);
    }

    public void deletePost(int id) {
        postMapper.deletePost(id);
    }

    public Optional<Post> findPostById(int id) {
        return Optional.ofNullable(postMapper.findPostById(id));
    }

    public int countPostByCategoryId(int categoryId) {
        return postMapper.countPostByCategoryId(categoryId);
    }

}

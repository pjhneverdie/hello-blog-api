package com.jj.hello_blog.domain.post.repository;


import com.jj.hello_blog.domain.post.dto.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final PostMapper postMapper;

    public Post save(Post post) {
        postMapper.savePost(post);
        return post;
    }

    public void updatePost(Post post) {
        postMapper.updatePost(post);
    }

    public void deletePostById(int id) {
        postMapper.deletePostById(id);
    }

    public Optional<Post> findPostById(int id) {
        return Optional.ofNullable(postMapper.findPostById(id));
    }

    public int countPostByCategoryId(int categoryId) {
        return postMapper.countPostByCategoryId(categoryId);
    }

}

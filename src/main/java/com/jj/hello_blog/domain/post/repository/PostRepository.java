package com.jj.hello_blog.domain.post.repository;


import com.jj.hello_blog.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class PostRepository {

    private final PostMapper postMapper;

    public Post post(Post post) {
        postMapper.post(post);
        return post;
    }

    public Optional<Post> findPostById(int id) {
        return Optional.ofNullable(postMapper.findPostById(id));
    }

}

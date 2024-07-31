package com.jj.hello_blog.domain.post.repository;

import com.jj.hello_blog.domain.post.dto.Post;
import com.jj.hello_blog.domain.post.dto.PostUpdateDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {

    void savePost(Post post);

    void updatePost(PostUpdateDto postUpdateDto);

    void deletePost(int id);

    Post findPostById(int id);

    int countPostByCategoryId(int id);


}

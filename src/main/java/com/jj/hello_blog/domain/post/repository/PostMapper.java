package com.jj.hello_blog.domain.post.repository;

import com.jj.hello_blog.domain.post.dto.Post;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {

    void savePost(Post post);

    void updatePost(Post post);

    void deletePostById(int id);

    Post findPostById(int id);

    int countPostByCategoryId(int id);


}

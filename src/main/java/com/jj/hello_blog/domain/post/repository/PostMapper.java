package com.jj.hello_blog.domain.post.repository;

import com.jj.hello_blog.domain.post.entity.Post;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PostMapper {

    void post(Post post);

    Post findById(int id);

}

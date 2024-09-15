package com.jj.hello_blog.domain.post.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.jj.hello_blog.domain.post.dto.Post;
import com.jj.hello_blog.domain.post.dto.PostResponse;
import com.jj.hello_blog.domain.post.dto.PostUpdateQueryDto;
import com.jj.hello_blog.domain.post.dto.PostPaginationCond;

@Mapper
public interface PostMapper {

    void insertPost(Post post);

    Post selectPostById(int id);

    List<PostResponse> selectPostsOrderByCreatedAtDesc(PostPaginationCond postPaginationCond);

    List<PostResponse> selectPostsByCategoryIdOrderByCreatedAtDesc(int categoryId, PostPaginationCond postPaginationCond);

    void updatePostById(PostUpdateQueryDto postUpdateQueryDto);

    void deletePostById(int id);

}

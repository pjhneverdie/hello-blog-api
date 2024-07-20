package com.jj.hello_blog.domain.post.service;

import com.jj.hello_blog.domain.post.entity.Post;
import com.jj.hello_blog.domain.post.repository.PostRepository;
import com.jj.hello_blog.web.post.form.PostSaveForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public Post post(PostSaveForm postSaveForm) {
        Post post = new Post(null, postSaveForm.getTitle(),
                postSaveForm.getContent(), null, null, postSaveForm.getCategoryId());

        postRepository.post(post);

        return findPostById(post.getId());
    }

    private Post findPostById(int id) {
        Optional<Post> posted = postRepository.findById(id);

        // 옵셔널이 비었을 경우는 sql 오류 말고 가능성이 없고 트랜잭션이 걸려있으니
        // isPresent 확인 안 함
        return posted.get();
    }
}

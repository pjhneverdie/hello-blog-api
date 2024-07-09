package com.jj.hello_blog.web.config;

import com.jj.hello_blog.web.session.SignInCheckInterCeptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SignInCheckInterCeptor())
                .order(1)
                .addPathPatterns("/post/write");
    }
}

package com.jj.hello_blog.web.common.config;

import org.springframework.stereotype.Component;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import com.jj.hello_blog.web.common.session.SignInCheckInterceptor;

@Component
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SignInCheckInterceptor())
                .order(1)
                .addPathPatterns("/member/**", "/admin/**");
    }

}

package com.jj.hello_blog.web.config;

import com.jj.hello_blog.web.session.OwnerCheckInterCeptor;
import com.jj.hello_blog.web.session.SignInCheckInterCeptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private SignInCheckInterCeptor signInCheckInterceptor;

    @Autowired
    private OwnerCheckInterCeptor ownerCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(signInCheckInterceptor)
                .order(1)
                .addPathPatterns("/comment/**");

        registry.addInterceptor(ownerCheckInterceptor)
                .order(2)
                .addPathPatterns("/post/**");
    }
}

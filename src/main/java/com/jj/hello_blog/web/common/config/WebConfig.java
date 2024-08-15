package com.jj.hello_blog.web.common.config;

import com.jj.hello_blog.web.common.session.AdminCheckInterceptor;
import com.jj.hello_blog.web.common.session.SignInCheckInterCeptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private SignInCheckInterCeptor signInCheckInterceptor;

    @Autowired
    private AdminCheckInterceptor adminCheckInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(signInCheckInterceptor)
                .order(1)
                .addPathPatterns("/member/me", "/comment/**", "/like/**");

        registry.addInterceptor(adminCheckInterceptor)
                .order(2)
                .addPathPatterns("/admin/**");
    }
}

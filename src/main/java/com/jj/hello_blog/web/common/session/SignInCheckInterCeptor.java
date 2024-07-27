package com.jj.hello_blog.web.common.session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SignInCheckInterCeptor implements HandlerInterceptor {

    @Value("${blog.config.owner.password}")
    private String ownerPassword;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (ownerPassword.equals("test")) {
            return true;
        }

        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute(SessionConst.MEMBER_KEY) != null;
    }
}

package com.jj.hello_blog.web.session;

import com.jj.hello_blog.domain.member.entity.Member;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class OwnerCheckInterCeptor implements HandlerInterceptor {

    @Value("${blog.config.owner.password}")
    private String ownerPassword;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (ownerPassword.equals("test")) {
            return true;
        }

        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute(SessionConst.MEMBER_KEY) != null
                && checkOwnerPassword((Member) session.getAttribute(SessionConst.MEMBER_KEY));
    }

    private boolean checkOwnerPassword(Member member) {
        return member.getPassword().equals(ownerPassword);
    }
}

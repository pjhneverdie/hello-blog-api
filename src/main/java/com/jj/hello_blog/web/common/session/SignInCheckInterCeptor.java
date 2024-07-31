package com.jj.hello_blog.web.common.session;

import com.jj.hello_blog.web.common.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.http.HttpStatus;

import org.springframework.stereotype.Component;

@Component
public class SignInCheckInterCeptor extends SessionInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (ownerPassword.equals("test")) {
            return true;
        }

        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute(SessionConst.MEMBER_KEY) != null) {
            return true;
        }

        response.setStatus(HttpStatus.SC_UNAUTHORIZED);
        sendErrorResponse(response, new ApiResponse<>(null));
        return false;
    }
}

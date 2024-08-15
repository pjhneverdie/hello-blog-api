package com.jj.hello_blog.web.common.session;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.http.HttpStatus;

import org.springframework.stereotype.Component;

import com.jj.hello_blog.web.common.response.ApiResponse;

@Component
public class SignInCheckInterCeptor extends SessionInterceptor {

    private static final String[] EXCLUDED_PATHS = new String[]{
            "/member/signUp", "/member/signIn"
    };

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (ownerPassword.equals("test")) {
            return true;
        }

        for (String path : EXCLUDED_PATHS) {
            if (request.getRequestURI().equals(path)) {
                return true;
            }
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

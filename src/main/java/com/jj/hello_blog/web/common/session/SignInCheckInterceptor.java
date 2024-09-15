package com.jj.hello_blog.web.common.session;

import java.util.Arrays;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.jj.hello_blog.domain.common.exception.CustomException;
import com.jj.hello_blog.domain.common.exception.GlobalExceptionCode;

@Component
public class SignInCheckInterceptor implements HandlerInterceptor {

    private static final String[] EXCLUDE_PATH = {"member/signIn"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestURI = request.getRequestURI();

        // 인터셉터를 거치지 않아도 되는 경로는 통과
        if (Arrays.stream(EXCLUDE_PATH).anyMatch(requestURI::contains)) {
            return true;
        }

        // CORS Preflight 요청은 통과
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute(SessionConst.MEMBER_KEY) != null) {
            return true;
        }

        throw new CustomException(GlobalExceptionCode.UNAUTHORIZED);
    }

}

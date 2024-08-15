package com.jj.hello_blog.web.common.session;

import com.jj.hello_blog.domain.member.dto.Member;
import com.jj.hello_blog.web.common.response.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class OwnerCheckInterCeptor extends SessionInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (ownerPassword.equals("test")) {
            return true;
        }

        // 카테고리 GET 요청은 인터셉터를 거치지 않도록 함
        if ("GET".equalsIgnoreCase(request.getMethod()) && request.getRequestURI().startsWith("/category")) {
            return true;
        }

        HttpSession session = request.getSession(false);

        System.out.println(session == null);

        if (session != null && session.getAttribute(SessionConst.MEMBER_KEY) != null) {
            if (checkOwnerPassword((Member) session.getAttribute(SessionConst.MEMBER_KEY))) {
                return true;
            }
        }

        response.setStatus(HttpStatus.SC_UNAUTHORIZED);
        sendErrorResponse(response, new ApiResponse<>(null));
        return false;
    }

    private boolean checkOwnerPassword(Member member) {
        return member.getPassword().equals(ownerPassword);
    }

}

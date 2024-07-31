package com.jj.hello_blog.web.common.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jj.hello_blog.web.common.response.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    protected ObjectMapper objectMapper;

    @Value("${blog.config.owner.password}")
    protected String ownerPassword;

    protected void sendErrorResponse(HttpServletResponse response, ApiResponse<Void> apiResponse) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        apiResponse.setMessage("UNAUTHORIZED");
        response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
    }

}

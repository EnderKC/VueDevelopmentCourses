package com.example.score.handler;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 通过自定义AccessDeniedHandler我们可以自定义（403-禁止访问）响应的内容
 * <br>
 * @author lihuanzhe
 * @version 1.0
 */

@Component
public class MyAuthenticationAccessDeniedHandler implements AccessDeniedHandler {

    /**
     * 访问拒绝处理
     * @param request that resulted in an <code>AccessDeniedException</code>
     * @param response so that the user agent can be advised of the failure
     * @param accessDeniedException that caused the invocation
     * @throws IOException 异常
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write("很抱歉，您没有访问权限");
    }
}

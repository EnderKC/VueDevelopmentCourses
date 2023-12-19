package com.example.score.handler;

import com.example.common.core.R;
import com.example.common.exception.CustomException;
import com.example.common.util.MessageUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestBindingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 身份验证成功处理
 * <br>
 * @author lihuanzhe
 * @version 1.0
 */
@Component(value = "myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    /**
     * 构造函数
     */
    public MyAuthenticationSuccessHandler() {

    }

    /**
     * 验证成功处理
     * @param request the request which caused the successful authentication
     * @param response the response
     * @param authentication the Authentication object which was created during the authentication process.
     * @throws CustomException 自定义异常
     * @throws IOException     IO异常
     * @throws ServletRequestBindingException 异常
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws CustomException,IOException, ServletRequestBindingException {

        Object obj = authentication.getPrincipal();
        if(!(obj instanceof User)){
            throw new CustomException(MessageUtils.message("authentication.info.error.in.request.header"));
        }
        User user=(User) obj;

        HttpSession session = request.getSession();
        session.setAttribute("user",user);

        response.setContentType("application/json;charset=UTF-8");
        R r=R.ok().put("data",user.getUsername());
        response.getWriter().write(r.toJSONString());

    }

}

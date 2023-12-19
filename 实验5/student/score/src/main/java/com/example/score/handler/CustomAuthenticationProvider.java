package com.example.score.handler;

import com.example.common.util.MessageUtils;
import com.example.score.service.MyUserDetailService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

/**
 * 自定义身份认证器
 * <br>
 *
 * @author lihuanzhe
 * @version 1.0
 */
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    /**
     * 用户信息服务
     */
    private final MyUserDetailService userDetailsService;

    /**
     * 构造函数
     *
     * @param userDetailsService 用户信息服务
     */
    public CustomAuthenticationProvider(MyUserDetailService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    /**
     * 自定义身份认证,检查用户提交的凭证
     * @param authentication 用户提交的认证信息
     * @return 认证信息
     * @throws AuthenticationException 认证异常
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // 从 authentication 中获取用户名和密码
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails userDetails=null;
        try {
            userDetails = userDetailsService.loadUserByUsername(username);
        }catch(Exception e){
            throw new UsernameNotFoundException(e.getMessage());
        }

        // 如果认证通过，返回一个 Authentication 实例
        if(password.equals(userDetails.getPassword())){
            return new CustomAuthenticationToken(userDetails, userDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("无效的用户名或密码");
        }
    }

    /**
     * 是否支持自定义身份认证
     * @param authentication 认证信息
     * @return 是否支持
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

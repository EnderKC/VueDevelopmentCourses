package com.example.score.service;

import com.example.common.exception.CustomException;
import com.example.common.util.MessageUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * UserDetailService类，用于对用户信息进行验证
 * <br>
 *
 * @author lihuanzhe
 * @version 1.0
 */
@Service
public class MyUserDetailService implements UserDetailsService {

    /**
     * 访问用户信息接口服务
     */
    private final UserService userService;

    /**
     * 构造函数
     *
     * @param userService 访问用户信息接口
     */
    public MyUserDetailService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 根据账号查询用户信息
     *
     * @param username 用户名
     * @return UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, CustomException {
        com.example.score.entity.User user=userService.getEntityById(username);

        //如果用户查不到，返回null，由provider来抛出异常
        if(user == null){
            throw new UsernameNotFoundException(MessageUtils.message("user.not.exist"));
        }

        //根据用户的id查询用户的权限
        List<String> roles=new ArrayList<>();
        roles.add(user.getRole());

        //将permissions转成数组
        String[] roleArray = new String[roles.size()];
        roles.toArray(roleArray);

        //经过UserDetails重新封装后，roles会加上ROLE_前缀
        UserDetails userDetails= User.withUsername(username)
                .password(user.getPassword())
                .disabled(false)
                .accountExpired(false)
                .credentialsExpired(false)
                .accountLocked(false)
                .roles(roleArray)
                .build();

        return new User(userDetails.getUsername(), userDetails.getPassword(),
                userDetails.isEnabled(), userDetails.isAccountNonExpired(), userDetails.isCredentialsNonExpired(),
                userDetails.isAccountNonLocked(), userDetails.getAuthorities());
    }
}

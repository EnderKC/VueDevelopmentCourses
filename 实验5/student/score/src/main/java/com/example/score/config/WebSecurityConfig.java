package com.example.score.config;

import com.example.common.util.MessageUtils;
import com.example.score.handler.MyAuthenticationAccessDeniedHandler;
import com.example.score.handler.MyAuthenticationFailureHandler;
import com.example.score.handler.MyAuthenticationSuccessHandler;
import com.example.score.handler.MyLogOutSuccessHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * 应用安全配置
 * <br>
 *
 * @author lihuanzhe
 * @version 1.0
 * @EnableWebSecurity 启用 Spring Security 的 web 安全功能
 * @EnableGlobalMethodSecurity prePostEnabled 属性需要设置为 true 表示开启方法级别的权限拦截。
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    /**
     * 请求认证白名单，不需要认证的请求
     */
    private static final String[] AUTH_WHITELIST = new String[]{
            "/authentication/require",
            "/session/invalid",
            "/code/**",
            "/druid/**",
            "/user/register",
            "/user/checkUserName/*",
            "/stuinfo/checkStuId/*"

    };

    /**
     * 身份验证成功处理
     */
    private final MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    /**
     * 身份验证失败处理
     */
    private final MyAuthenticationFailureHandler authenticationFailureHandler;

    /**
     * 自定义（403-禁止访问）响应的内容
     */
    private final MyAuthenticationAccessDeniedHandler authenticationAccessDeniedHandler;


    /**
     * 退出成功处理
     */
    private final MyLogOutSuccessHandler logOutSuccessHandler;

    /**
     * 对象转换器
     */
    private final ObjectMapper objectMapper;

    /**
     * 构造器注入
     *
     * @param authenticationSuccessHandler      身份验证成功处理
     * @param authenticationFailureHandler      身份验证失败处理
     * @param authenticationAccessDeniedHandler 自定义（403-禁止访问）响应的内容
     * @param logOutSuccessHandler              退出成功处理
     * @param mapper
     */
    public WebSecurityConfig(@Qualifier("myAuthenticationSuccessHandler") MyAuthenticationSuccessHandler authenticationSuccessHandler,
                             MyAuthenticationFailureHandler authenticationFailureHandler,
                             MyAuthenticationAccessDeniedHandler authenticationAccessDeniedHandler,
                             MyLogOutSuccessHandler logOutSuccessHandler, ObjectMapper mapper) {
        this.myAuthenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
        this.authenticationAccessDeniedHandler = authenticationAccessDeniedHandler;
        this.logOutSuccessHandler = logOutSuccessHandler;
        this.objectMapper = mapper;
    }

    /**
     * session的创建和session的销毁都会被 HttpSessionEventPublisher 监控,
     * 这种方式可以及时清理session的记录，以确保最新的session状态可以被及时感知到。
     *
     * @return
     */
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    /**
     * 给登录页面放行
     * Spring Security 给一个地址放行，有两种方式：
     * 1. 被放行的资源，不需要经过 Spring Security 过滤器链（静态资源一般使用这种）。
     * 2. 经过 Spring Security 过滤器链，但是不拦截（如果是一个接口想要匿名访问，一般使用这种）。
     * <p>
     * 很明显下面这种方形方式是第一种
     *
     * @return
     */
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().antMatchers(AUTH_WHITELIST);
    }
    /**
     * 自己手动配置安全过滤器链
     *
     * @param http 安全过滤器链
     * @return SecurityFilterChain
     * @throws Exception 异常
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // 禁用CSRF保护，允许跨域访问
                .csrf().disable()
                //开启 cors() 后，你可以进一步配置 CORS 参数，比如允许的方法、头部信息、以及前端发起请求时可访问的域名等
                .cors().and()
                // 设置 session 配置管理
                .sessionManagement()
                // 设置 session 失效处理
                .invalidSessionUrl("/session/invalid")
                // 设置session创建策略
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                // 设置最大会话数
                .maximumSessions(1)
                //设置false允许多点登录,但是如果超出最大人数之前的登录会被踢掉
                .maxSessionsPreventsLogin(false)
                //会话过期策略
                .expiredSessionStrategy(event -> {
                    HttpServletResponse response = event.getResponse();
                    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                    HashMap<String, Object> result = new HashMap<>();
                    result.put("status", HttpStatus.UNAUTHORIZED.value());
                    result.put("msg", MessageUtils.message("invalid.session"));
                    String s = objectMapper.writeValueAsString(result);
                    response.getWriter().write(s);
                    response.flushBuffer();
                })
                .and()
                .and()
                .exceptionHandling()
                .accessDeniedHandler(authenticationAccessDeniedHandler)
                .and()
                // 表单登录
                .formLogin()
                // 登录跳转 URL
                .loginPage("/authentication/require")
                // 处理表单登录 URL
                .loginProcessingUrl("/login")
                // 处理登录成功
                .successHandler(myAuthenticationSuccessHandler)
                // 处理登录失败
                .failureHandler(authenticationFailureHandler)
                .and()
                .authorizeHttpRequests()
                //允许对于网站中的所有请求进行预检请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                // 所有请求
                .anyRequest()
                // 都需要认证
                .authenticated()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessHandler(logOutSuccessHandler)
        ;

        return http.build();
    }


}

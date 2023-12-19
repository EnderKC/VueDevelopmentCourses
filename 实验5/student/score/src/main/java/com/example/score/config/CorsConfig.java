package com.example.score.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;

/**
 * 如果您在使用 Spring Security，那么您需要在 Spring Security 的配置中显式地允许跨域请求。这是因为 Spring Security
 * 会对所有进入应用的请求进行拦截和处理，所以您必须配置它以允许 CORS。
 * @author lihuanzhe
 */
@Configuration
public class CorsConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();

        //设置允许的域
        configuration.addAllowedOrigin("http://localhost:8000");
        //设置允许的HTTP方法
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        //设置允许的HTTP请求头
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        //设置浏览器是否应该发送凭据，如 cookies。
        configuration.setAllowCredentials(true);

        //为所有路径应用这个配置
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
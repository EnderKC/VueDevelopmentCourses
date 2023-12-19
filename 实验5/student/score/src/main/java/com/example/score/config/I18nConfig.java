package com.example.score.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * 国际化配置
 * <br>
 * @author lihuanzhe
 * @version 1.0
 */
@Configuration
public class I18nConfig implements WebMvcConfigurer {
    /**
     * 将Locale信息存储在session中，如果用户想要修改Locale信息，可以通过修改session中对应属性的值即可
     * 设置默认的方言
     * @return LocaleResolver
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        // 默认语言
        slr.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
        return slr;
    }

    /**
     * LocaleChangeInterceptor拦截器来更改地区信息。它能检测请求中的参数，并根据其值相应地更新地区信息
     * http://localhost:8080/message?lang=en_US  切换为英文
     * http://localhost:8080/message?lang=zh_CN  切换为中文
     * @return LocaleChangeInterceptor
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        // 参数名
        lci.setParamName("lang");
        return lci;
    }

    /**
     * 把方言切换拦截器加入拦截器链
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}

package com.example.common.core;


import com.example.common.util.ServletUtils;
import com.example.common.util.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * controller的基础类，对controller的通用方法进行了封装，在此类的基础上可以派生其它controller
 * <br>
 * @author lihuanzhe
 * @version 1.0
 */
public class BaseController
{

    /**
     *  获取上下文件中的请求对象
     *
     * @return jakarta.servlet.http.HttpServletRequest
     */
    protected HttpServletRequest getRequest() {
        return ServletUtils.getRequest();
    }

    /**
     *  获取上下文中的响应对象
     *
     * @return jakarta.servlet.http.HttpServletResponse
     
     */
    protected HttpServletResponse getResponse() {
        return ServletUtils.getResponse();
    }

    /**
     *  获取上下文中的session对象
     *
     * @return jakarta.servlet.http.HttpSession
     
     */
    protected HttpSession getSession() {
        return getRequest().getSession();
    }


    /**
     *  格式化页面跳转字符串
     *
     * @param url 要跳转到的地址
     * @return java.lang.String
     
     */
    protected String redirect(String url) {
        return StringUtils.format("redirect:{}", url);
    }

    /**
     * 响应返回结果
     *
     * @param rows 影响行数
     * @return 操作结果，rows>0返回ok()，否则返回error()
     */
    protected R toAjax(int rows) {
        return rows > 0 ? R.ok() : R.error();
    }

    /**
     * 响应返回结果
     *
     * @param result 结果，
     * @return 操作结果，result为true返回ok()，为false返回error()
     */
    protected R toAjax(boolean result) {
        return result ? R.ok() : R.error();
    }

    /**
     * Spring MVC和Spring Boot的默认 json解析器便是 Jackson，它可以帮助我们快速的进行各个类型和Json类型的相互转换。
     *
     * @return com.fasterxml.jackson.databind.ObjectMapper
     **/
    protected ObjectMapper getMapper() {
        ObjectMapper mapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
        javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        mapper.registerModule(javaTimeModule);
        return mapper;
    }


}

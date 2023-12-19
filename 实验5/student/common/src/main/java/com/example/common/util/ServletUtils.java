package com.example.common.util;


import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * web客户端工具类，提供对web访问的一些常用接口
 * <br>
 * @author lihuanzhe
 * @version 1.0
 */
public class ServletUtils {
    /**
     *  根据参数名获取请求中的参数值
     *
     * @param name 参数名称
     * @return java.lang.String

     */
    public static String getParameter(String name) {
        return getRequest().getParameter(name);
    }

    /**
     *  根据参数名获取请求中的参数值，参数不存在时返回默认值
     *
     * @param name 参数名
     * @param defaultValue 默认值
     * @return java.lang.String

     */
    public static String getParameter(String name, String defaultValue) {
        return Convert.toStr(getRequest().getParameter(name), defaultValue);
    }

    /**
     *  根据参数名获取请求中的参数值
     *
     * @param name 参数名
     * @return java.lang.Integer

     */
    public static Integer getParameterToInt(String name) {
        return Convert.toInt(getRequest().getParameter(name));
    }

    /**
     *  根据参数名获取请求中的参数值，参数不存在时返回默认值
     *
     * @param name 参数名
     * @param defaultValue 默认值
     * @return java.lang.Integer

     */
    public static Integer getParameterToInt(String name, Integer defaultValue) {
        return Convert.toInt(getRequest().getParameter(name), defaultValue);
    }

    /**
     *  获取请求对象
     *
     * @return jakarta.servlet.http.HttpServletRequest

     */
    public static HttpServletRequest getRequest() {
        return getRequestAttributes().getRequest();
    }

    /**
     *  获取response对象
     *
     * @return jakarta.servlet.http.HttpServletResponse

     */
    public static HttpServletResponse getResponse() {
        return getRequestAttributes().getResponse();
    }

    /**
     *  获取session对象
     *
     * @return jakarta.servlet.http.HttpSession
     */
    public static HttpSession getSession() {
        return getRequest().getSession();
    }

    /**
     *  从上下文中获取ServletRequestAttributes对象
     *
     * @return org.springframework.web.context.request.ServletRequestAttributes

     */
    public static ServletRequestAttributes getRequestAttributes() {
        //通过RequestContextHolder的静态方法可以随时随地取到当前请求的request对象
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        return (ServletRequestAttributes) attributes;
    }


    /**
     * 是否是Ajax异步请求
     *
     * @param request 请求对象
     * @return boolean
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String accept = request.getHeader("accept");
        if (accept != null && accept.indexOf("application/json") != -1) {
            return true;
        }

        String xRequestedWith = request.getHeader("X-Requested-With");
        if (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1) {
            return true;
        }

        return false;
    }
}

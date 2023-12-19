package com.example.common.core;

import com.alibaba.fastjson2.JSON;
import com.example.common.util.DateTimeUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 后端返回给前端的响应结果，以json形式返回给前端，属性包括code状态码、msg响应消息、data封装的响应数据等
 * <br>
 * @author lihuanzhe
 * @version 1.0
 */
public class R extends HashMap<String, Object> {

    private static final long serialVersionUID = -8157613083634272196L;

    /**
     * 默认错误响应值
     */
    public static final int ERROR = 500;

    /**
     * 正确响应值
     */
    public static final int SUCCESS = 200;

    /**
     *  创建一个默认正确的响应对象
     *
     */
    public R() {
        put("code", SUCCESS);
        put("msg", "success");
        put("timestamp", DateTimeUtils.getCurrentDateTimeStr());
    }

    /**
     *  创建一个默认错误的响应对象
     *
     * @return R
     
     */
    public static R error() {
        return error(ERROR, "内部服务器错误，执行失败");
    }

    /**
     *  创建一个具有指定错误信息的错误响应对象
     *
     * @param msg 错误信息
     * @return R
     
     */
    public static R error(String msg) {
        return error(ERROR, msg);
    }

    /**
     *  创建一个具有错误码和错误信息的错误响应对象
     *
     * @param code 错误码
     * @param msg 错误信息
     * @return R
     */
    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    /**
     *  创建一个具有错误码、错误信息和请求路径的错误响应对象
     *
     * @param code 错误码
     * @param msg 错误信息
     * @param path 请求路径
     * @return R
     
     */
    public static R error(int code, String msg, String path) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        r.put("path", path);
        return r;
    }

    /**
     *  创建一个具有指定信息的正确响应对象
     *
     * @param msg 响应信息
     * @return R
     
     */
    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    /**
     *  创建一个具有响应信息和数据对象的正确响应对象
     *
     * @param msg 响应信息
     * @param data 要返回的数据对象，data可以返回一个值或json数据格式
     * @return R
     
     */
    public static R ok(String msg, Object data) {
        R r = new R();
        r.put("msg", msg);
        r.put("data", data);
        return r;
    }

    /**
     *  创建一个以map形式保存数据对象的正确响应对象
     *
     * @param map 要返回的参数信息
     * @return R
     
     */
    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    /**
     *  创建一个正确响应对象
     *
     * @return R
     
     */
    public static R ok() {
        return new R();
    }

    /**
     *  把当前对象转换为字符串
     *
     * @return java.lang.String
     
     */
    public String toJSONString() {
        return JSON.toJSONString(this);
    }

    /**
     *  向当前对象中添加键值对
     *
     * @param key 键值对的key
     * @param value 键值对的value
     * @return R
     
     */
    @Override
    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    /**
     *  取当前对象的code值
     *
     * @return int
     
     */
    public int getCode() {
        return (int) super.get("code");
    }

    /**
     *  取当前对象的msg属性值
     *
     * @return java.lang.String
     
     */
    public String getMsg() {
        return (String) super.get("msg");
    }
}

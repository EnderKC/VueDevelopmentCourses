package com.example.common.core;

/**
 * 通用常量定义
 * <br>
 * @author lihuanzhe
 * @version 1.0
 */
public class Constants {
    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 成功标记
     */
    public static final Integer SUCCESS = 200;

    /**
     * 失败标记
     */
    public static final Integer FAIL = 500;

    /**
     * 当前记录起始索引
     */
    public static final String PAGE_NUM = "pageNum";

    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 随机数 redis key
     */
    public static final String RANDOM_STR = "random_str";

    /**
     * 验证码有效期（分钟）
     */
    public static final long CAPTCHA_EXPIRATION = 3;

    /**
     * 请求中包含的token的key
     */
    public static final String JSON_TOKEN = "json-token";

    /**
     * token的md5值
     */
    public static final String TOKEN_MD5 = "token-md5";

    /**
     * 存储手机验证码的key
     */
    public static final String SMS_CODE_KEY = "sms_codes:";
    /**
     * 二维码登录的key
     */
    public static final String QR_CODE_KEY = "qr_codes:";
    /**
     * 服务降级提示信息
     */
    public static final String FALLBACK_MSG = "内部接口暂时不可用，hystrix服务降级处理:";
    /**
     * 参数为空提示信息
     */
    public static final String PARAMETER_NULL_MSG = "方法参数不能为空";

    /**
     * 参数为空提示信息
     */
    public static final String FILLUPLOAD_ERROR = "上传失败";

    /**
     * 参数错误提示信息
     */
    public static final String PARAMETER_ERROR_MSG = "方法参数错误";

}

package com.example.common.exception;


/**
 * 自定义异常，这里可以定义一外详细的异常列表，以区分不同的异常
 * <br>
 * @author lihuanzhe
 * @version 1.0
 */
public class CustomException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * 失败状态码
     */
    public static final int FAIL = 600;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 错误消息
     */
    private String message;

    /**
     * 构造函数
     * @param message 异常信息
     */
    public CustomException(String message)
    {
        this.message = message;
        this.code=FAIL;
    }

    /**
     * 构造函数
     * @param code 异常状态码
     * @param message 异常信息
     */
    public CustomException(Integer code,String message)
    {
        this.message = message;
        this.code = code;
    }

    /**
     * 构造函数
     * @param message 异常信息
     * @param e 抛出的异常
     */
    public CustomException(String message, Throwable e)
    {
        super(message, e);
        this.message = message;
    }

    /**
     * 获取异常信息
     * @return 异常信息
     */
    @Override
    public String getMessage()
    {
        return message;
    }

    /**
     * 获取状态码
     * @return 状态码
     */
    public Integer getCode()
    {
        return code;
    }
}

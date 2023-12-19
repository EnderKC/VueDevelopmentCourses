package com.example.common.exception.file;


/**
 * 文件异常类
 * <br>
 * @author lihuanzhe
 * @version 1.0
 */
public class FileException extends RuntimeException
{
    private static final long serialVersionUID = 1L;

    /**
     * 失败状态码
     */
    public static final int FAIL = 500;

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
    public FileException(String message)
    {
        this.message = message;
        this.code=FAIL;
    }

    /**
     * 构造函数
     * @param code 状态码
     * @param message 异常信息
     */
    public FileException(Integer code,String message)
    {
        this.message = message;
        this.code = code;
    }

    /**
     * 构造函数
     * @param e 抛出的异常
     * @param message 异常信息
     */
    public FileException(String message, Throwable e)
    {
        super(message, e);
        this.message = message;
    }

    /**
     * 获取异常信息
     * @return String
     */
    @Override
    public String getMessage()
    {
        return message;
    }

    /**
     * 获取异常状态码
     * @return 异常状态码
     */
    public Integer getCode()
    {
        return code;
    }
}

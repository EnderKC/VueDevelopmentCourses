package com.example.common.exception.file;


import com.example.common.util.StringUtils;

/**
 * 文件名大小限制异常类
 * <br>
 * @author lihuanzhe
 * @version 1.0
 */
public class FileSizeLimitExceededException extends FileException
{
    private static final long serialVersionUID = 1L;

    /**
     * 构造函数
     * @param defaultMaxSize 最大上传文件尺寸
     */
    public FileSizeLimitExceededException(long defaultMaxSize)
    {
        super(StringUtils.format("upload.exceed.maxSize:{}", defaultMaxSize));
    }
}

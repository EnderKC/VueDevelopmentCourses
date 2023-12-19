package com.example.common.exception.file;


import com.example.common.util.StringUtils;

/**
 * 文件名称超长限制异常类
 * <br>
 * @author lihuanzhe
 * @version 1.0
 */
public class FileNameLengthLimitExceededException extends FileException
{
    private static final long serialVersionUID = 1L;

    /**
     * 构造函数
     * @param defaultFileNameLength 默认文件名长度
     */
    public FileNameLengthLimitExceededException(int defaultFileNameLength)
    {
        super(StringUtils.format("upload.filename.exceed.length:{}", defaultFileNameLength));
    }
}

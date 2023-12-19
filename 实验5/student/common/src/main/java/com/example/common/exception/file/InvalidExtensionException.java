package com.example.common.exception.file;


import org.apache.tomcat.util.http.fileupload.FileUploadException;

import java.util.Arrays;

/**
 * 文件上传 误异常类
 * <br>
 * @author lihuanzhe
 * @version 1.0
 */
public class InvalidExtensionException extends FileUploadException
{
    private static final long serialVersionUID = 1L;

    /**
     * 允许的扩展名
     */
    private String[] allowedExtension;
    /**
     * 扩展名
     */
    private String extension;
    /**
     * 文件名
     */
    private String filename;

    /**
     * 构造函数
     * @param allowedExtension  允许的扩展名
     * @param extension         扩展名
     * @param filename          文件名
     */
    public InvalidExtensionException(String[] allowedExtension, String extension, String filename)
    {
        super("filename : [" + filename + "], extension : [" + extension + "], allowed extension : [" + Arrays.toString(allowedExtension) + "]");
        this.allowedExtension = allowedExtension;
        this.extension = extension;
        this.filename = filename;
    }

    /**
     * 获取允许的扩展名
     *
     * @return 返回扩展名数组
     */
    public String[] getAllowedExtension()
    {
        return allowedExtension;
    }

    /**
     * 返回扩展名
     *
     * @return 返回扩展名
     */
    public String getExtension()
    {
        return extension;
    }

    /**
     * 返回文件名
     *
     * @return 返回文件名
     */
    public String getFilename()
    {
        return filename;
    }

    /**
     * 无效的图片扩展名异常
     */
    public static class InvalidImageExtensionException extends InvalidExtensionException
    {
        private static final long serialVersionUID = 1L;

        /**
         * 构造函数
         * @param allowedExtension  允许的扩展名
         * @param extension         扩展名
         * @param filename          文件名
         */
        public InvalidImageExtensionException(String[] allowedExtension, String extension, String filename)
        {
            super(allowedExtension, extension, filename);
        }
    }

    /**
     * 无效的flash扩展名
     */
    public static class InvalidFlashExtensionException extends InvalidExtensionException
    {
        private static final long serialVersionUID = 1L;

        /**
         * 构造函数
         * @param allowedExtension  允许的扩展名
         * @param extension         扩展名
         * @param filename          文件名
         */
        public InvalidFlashExtensionException(String[] allowedExtension, String extension, String filename)
        {
            super(allowedExtension, extension, filename);
        }
    }

    /**
     * 无效的媒体扩展名
     */
    public static class InvalidMediaExtensionException extends InvalidExtensionException
    {
        private static final long serialVersionUID = 1L;

        /**
         * 构造函数
         * @param allowedExtension  允许的扩展名
         * @param extension         扩展名
         * @param filename          文件名
         */
        public InvalidMediaExtensionException(String[] allowedExtension, String extension, String filename)
        {
            super(allowedExtension, extension, filename);
        }
    }
    /**
     * 无效的视频文件扩展名
     */
    public static class InvalidVideoExtensionException extends InvalidExtensionException
    {
        private static final long serialVersionUID = 1L;

        /**
         * 构造函数
         * @param allowedExtension  允许的扩展名
         * @param extension         扩展名
         * @param filename          文件名
         */
        public InvalidVideoExtensionException(String[] allowedExtension, String extension, String filename)
        {
            super(allowedExtension, extension, filename);
        }
    }
}

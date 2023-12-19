package com.example.common.util.file;

/**
 * 媒体类型工具类
 * <br>
 * @author lihuanzhe
 * @version 1.0
 */
public class MimeTypeUtils
{
    /**
     * png文件
     */
    public static final String IMAGE_PNG = "image/png";

    /**
     * jpg文件
     */
    public static final String IMAGE_JPG = "image/jpg";

    /**
     * jpeg文件
     */
    public static final String IMAGE_JPEG = "image/jpeg";

    /**
     * bmp文件
     */
    public static final String IMAGE_BMP = "image/bmp";

    /**
     * gif文件
     */
    public static final String IMAGE_GIF = "image/gif";

    /**
     * 图片文件扩展名列表，"bmp", "gif", "jpg", "jpeg", "png"
     */
    public static final String[] IMAGE_EXTENSION = { "bmp", "gif", "jpg", "jpeg", "png" };

    /**
     * flash文件，"swf", "flv"
     */
    public static final String[] FLASH_EXTENSION = { "swf", "flv" };

    /**
     * 媒体文件扩展名，"swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg",
     *             "asf", "rm", "rmvb"
     */
    public static final String[] MEDIA_EXTENSION = { "swf", "flv", "mp3", "wav", "wma", "wmv", "mid", "avi", "mpg",
            "asf", "rm", "rmvb" };

    /**
     * 视频文件，"mp4", "avi", "rmvb"
     */
    public static final String[] VIDEO_EXTENSION = { "mp4", "avi", "rmvb" };

    /**
     * 允许的文件类型
     */
    public static final String[] DEFAULT_ALLOWED_EXTENSION = {
            // 图片
            "bmp", "gif", "jpg", "jpeg", "png",
            // word excel powerpoint
            "doc", "docx", "xls", "xlsx", "ppt", "pptx", "html", "htm", "txt",
            // 压缩文件
            "rar", "zip", "gz", "bz2",
            // 视频格式
            "mp4", "avi", "rmvb",
            // pdf
            "pdf" };

    /**
     * 根据文件类型获取扩展名
     * @param prefix 文件类型，类型前缀
     * @return string
     */
    public static String getExtension(String prefix)
    {
        switch (prefix)
        {
            case IMAGE_PNG:
                return "png";
            case IMAGE_JPG:
                return "jpg";
            case IMAGE_JPEG:
                return "jpeg";
            case IMAGE_BMP:
                return "bmp";
            case IMAGE_GIF:
                return "gif";
            default:
                return "";
        }
    }
}

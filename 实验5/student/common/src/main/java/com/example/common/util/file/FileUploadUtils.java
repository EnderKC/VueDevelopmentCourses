package com.example.common.util.file;


import com.example.common.exception.file.FileNameLengthLimitExceededException;
import com.example.common.exception.file.FileSizeLimitExceededException;
import com.example.common.util.DateTimeUtils;
import com.example.common.util.Md5Utils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * 文件上传工具类
 * <br>
 * @author lihuanzhe
 * @version 1.0
 */
public class FileUploadUtils {
    /**
     * 默认大小 500M
     */
    public static final long DEFAULT_MAX_SIZE = 500 * 1024 * 1024;

    /**
     * 默认的文件名最大长度 100
     */
    public static final int DEFAULT_FILE_NAME_LENGTH = 100;

    /**
     * 默认上传的地址
     */
    private static String defaultBaseDir = "upload";

    /**
     * 默认文件类型jpg
     */
    public static final String IMAGE_JPG_EXTENSION = ".jpg";

    private static int counter = 0;

    /**
    * 设置默认上传地址
    *
    * @param defaultBaseDir 默认上传地址
     */
    public static void setDefaultBaseDir(String defaultBaseDir) {
        FileUploadUtils.defaultBaseDir = defaultBaseDir;
    }

    /**
    * 获取默认上传地址
    *
    * @return java.lang.String
     */
    public static String getDefaultBaseDir() {
        String projectPath = System.getProperty("user.dir");
        return projectPath+File.separator+defaultBaseDir;
    }

    /**
     * 以默认配置进行文件上传
     *
     * @param file 上传的文件
     * @return 文件名称
     * @throws IOException IO异常
     */
    public static final String upload(MultipartFile file) throws IOException {
        try {
            return upload(getDefaultBaseDir(), file);
        } catch (Exception e) {
            throw new IOException(e.getMessage(), e);
        }
    }

    /**
     * 文件上传
     *
     * @param baseDir   相对应用的基目录
     * @param file      上传的文件
     * @return 返回上传成功的文件名
     * @throws FileSizeLimitExceededException       如果超出最大大小
     * @throws FileNameLengthLimitExceededException 文件名太长
     * @throws IOException                          比如读写文件出错时
     */
    public static final String upload(String baseDir, MultipartFile file)
            throws FileSizeLimitExceededException, IOException, FileNameLengthLimitExceededException {

        int fileNameLength = file.getOriginalFilename().length();
        if (fileNameLength > FileUploadUtils.DEFAULT_FILE_NAME_LENGTH) {
            throw new FileNameLengthLimitExceededException(FileUploadUtils.DEFAULT_FILE_NAME_LENGTH);
        }

        assertAllowed(file);

        String fileName = makeFilePathName(file);

        File desc = createFile(baseDir, fileName);
        file.transferTo(desc);
        return fileName;
    }

    /**
    * 创建要生成的文件相对路径及文件名
    *
    * @param file 要保存的文件
    * @return java.lang.String
     */
    public static final String makeFilePathName(MultipartFile file) {
        String filename = file.getOriginalFilename();
        String extension=FileUtils.getFileType(filename);
        filename = DateTimeUtils.getCurrentDateStr("yyyy/MM/")  + makeMd5Filename(filename) +"."+ extension;
        return filename;
    }

    /**
    * 在指定路径下创建文件
    *
    * @param uploadDir 指定的路径
     * @param filename 文件名
    * @return java.io.File
    * @throws
    */
    private static File createFile(String uploadDir, String filename) throws IOException {
        String filePath=uploadDir + File.separator + filename;
        File desc = new File(filePath);
        if (!desc.getParentFile().exists()) {
            desc.getParentFile().mkdirs();
        }
        if (!desc.exists()) {
            desc.createNewFile();
        }
        return desc;
    }

    /**
    * 生在md5文件名
    *
    * @param filename 文件名
    * @return java.lang.String
    * @throws
    */
    private static String makeMd5Filename(String filename) {

        return Md5Utils.hash(filename + System.nanoTime() + counter++);
    }

    /**
     * 文件大小校验
     *
     * @param file 上传的文件
     * @throws FileSizeLimitExceededException 如果超出最大大小
     */
    public static final void assertAllowed(MultipartFile file) throws FileSizeLimitExceededException {
        long size = file.getSize();
        if (DEFAULT_MAX_SIZE != -1 && size > DEFAULT_MAX_SIZE) {
            throw new FileSizeLimitExceededException(DEFAULT_MAX_SIZE / 1024 / 1024);
        }
    }


}

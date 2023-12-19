package com.example.common.util.file;


import com.example.common.exception.GlobalExceptionHandler;
import com.example.common.exception.file.FileException;
import com.example.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件处理工具类
 * <br>
 * @author lihuanzhe
 * @version 1.0
 */
@Slf4j
public class FileUtils {

    /** 字符常量：斜杠 {@code '/'} */
    public static final char SLASH = '/';

    /** 字符常量：反斜杠 {@code '\\'} */
    public static final char BACKSLASH = '\\';

    /**
     * 文件名模式
     */
    public static String FILENAME_PATTERN = "[a-zA-Z0-9_\\-\\|\\.\\u4e00-\\u9fa5]+";

    /**
    * 获取路径的父路径
    *
    * @param path 路径
    * @return java.lang.String
     */
    public static String getParentPath(String path)
    {
        int pos=path.lastIndexOf(File.separator);
        if(pos==-1){
            return path;
        }
        return path.substring(0,pos);
    }
    /**
     * 创建文件
     * @param filePath 创建文件的路径
     * @return BufferedWriter
     */
    public static BufferedWriter createFile(String filePath) {
        int pos=filePath.lastIndexOf(File.separator);
        String path=filePath.substring(0,pos);
        if(!fileExist(path)){
            createDirectory(path);
        }
        BufferedWriter out=null;
        try {
            out = new BufferedWriter(new FileWriter(filePath));
        }catch(IOException e)
        {
            log.error(GlobalExceptionHandler.getStackTraceInfo(e));
            out=null;
        }
        return out;
    }

    /**
     * 打开文件
     * @param filePath 打开文件的路径
     * @return BufferedReader
     * @throws FileNotFoundException 文件未找到异常
     */
    public static BufferedReader openFile(String filePath) throws FileNotFoundException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        }

        BufferedReader reader=null;
        try{
            reader=new BufferedReader(new FileReader(filePath));
        }catch(IOException e)
        {
            log.error(GlobalExceptionHandler.getStackTraceInfo(e));
            reader=null;
        }
        return reader;
    }

    /**
     * 删除文件
     * @param filePath 要删除文件的路径
     * @return 删除是否成功
     */
    public static boolean deleteFile(String filePath)
    {
        try{
            File file =new File(filePath);
            if(file.delete()){
                return true;
            }else{
                return false;
            }
        }catch(Exception e){
            log.error(GlobalExceptionHandler.getStackTraceInfo(e));
            return false;
        }
    }
    /**
     * 删除文件夹
     *
     * @param folderPath 要删除的文件的路径
     * @return 删除目录是否成功
     */
    public static boolean deleteFolder(String folderPath) {
        try {
            //删除完里面所有内容
            deleteAllFile(folderPath);
            String filePath = folderPath;
            File myFilePath = new File(filePath);
            //删除空文件夹
            return myFilePath.delete();
        } catch (Exception e) {
            log.error(GlobalExceptionHandler.getStackTraceInfo(e));
            return false;
        }
    }

    /**
     * 删除文件夹下的所有文件
     *
     * @param path 要删除的文件的路径
     * @return 删除是否成功
     */
    public static boolean deleteAllFile(String path) {
        boolean flag = false;
        File file = new File(path);
        if (!file.exists()) {
            return false;
        }
        if (!file.isDirectory()) {
            return false;
        }
        String[] tempList = file.list();
        File temp = null;
        for (int i = 0; i < tempList.length; i++) {
            if (path.endsWith(File.separator)) {
                temp = new File(path + tempList[i]);
            } else {
                temp = new File(path + File.separator + tempList[i]);
            }
            if (temp.isFile()) {
                flag=temp.delete();
            }
            if (temp.isDirectory()) {
                //先删除文件夹里面的文件
                deleteAllFile(path + "/" + tempList[i]);
                //再删除空文件夹
                flag=deleteFolder(path + "/" + tempList[i]);
            }
        }
        return flag;
    }
    /**
     * 如果目录不存在，则创建目录
     *
     * @param destParam 要被创建的目录
     * @return 创建目录是否成功
     */
    public static boolean createDirectory(String destParam) {
        File destDir = new File(destParam);
        if (!destDir.exists()) {
            return destDir.mkdirs();
        }
        return true;
    }

    /**
     * 功能描述: 判断一个文件或目录是否存在
     *
     * @param filepath 文件或目录路径
     * @return boolean
     */
    public static boolean fileExist(String filepath) {
        File file = new File(filepath);
        if (file.exists()) {
            return true;
        }
        return false;
    }

    /**
     * 检测目录是否为空
     * @param dirPath 目录
     * @return boolean
     */
    public static boolean dirIsEmpty(String dirPath)
    {
        if(!fileExist(dirPath)){
            return createDirectory(dirPath);
        }else{
            File file = new File(dirPath);
            String[] tempList = file.list();
            return tempList.length==0;
        }
    }
    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     * @throws FileNotFoundException 文件未找到异常
     */
    public static boolean copyFile(String oldPath, String newPath) throws FileNotFoundException {
        File file = new File(oldPath);
        if (!file.exists()) {
            throw new FileNotFoundException(oldPath);
        }
        InputStream inStream = null;
        FileOutputStream fs = null;
        boolean flag = true;
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) {
                inStream = new FileInputStream(oldPath);
                fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[10240];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread;
                    fs.write(buffer, 0, byteread);
                }
            }
        } catch (Exception e) {
            log.error(GlobalExceptionHandler.getStackTraceInfo(e));
            flag = false;
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }
                if (fs != null) {
                    fs.flush();
                    fs.close();
                }
            } catch (IOException e) {
                log.error(GlobalExceptionHandler.getStackTraceInfo(e));
                flag = false;
            }
        }
        return flag;
    }

    /**
     * 复制整个文件夹内容
     *
     * @param oldPath String 原文件路径 如：c:/fqf
     * @param newPath String 复制后路径 如：f:/fqf/ff
     * @return boolean
     */
    public static boolean copyFolder(String oldPath, String newPath) {
        FileInputStream input = null;
        FileOutputStream output = null;
        boolean flag = true;
        try {
            (new File(newPath)).mkdirs();
            File a = new File(oldPath);
            if(!a.exists()){
                return false;
            }
            String[] file = a.list();
            File temp = null;
            for (int i = 0; i < file.length; i++) {
                if (oldPath.endsWith(File.separator)) {
                    temp = new File(oldPath + file[i]);
                } else {
                    temp = new File(oldPath + File.separator + file[i]);
                }
                if (temp.isFile()) {
                    input = new FileInputStream(temp);
                    output = new FileOutputStream(newPath + File.separator + (temp.getName()).toString());
                    byte[] b = new byte[1030 * 5];
                    int len;
                    while ((len = input.read(b)) != -1) {
                        output.write(b, 0, len);
                    }
                    output.flush();
                    output.close();
                    input.close();
                    input = null;
                    output = null;
                }
                if (temp.isDirectory()) {
                    copyFolder(oldPath + File.separator + file[i], newPath + File.separator + file[i]);
                }
            }
        } catch (Exception e) {
            log.error(GlobalExceptionHandler.getStackTraceInfo(e));
            flag = false;
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
                if (output != null) {
                    output.flush();
                    output.close();
                }
            } catch (IOException e) {
                log.error(GlobalExceptionHandler.getStackTraceInfo(e));
                flag = false;
            }
        }
        return flag;
    }
    /**
     * 功能描述: 在指定目录下查找具有指定扩展名的文件，返回一个所有找到文件的相对路径列表
     *
     * @param dir       要查找的目录
     * @param baseDir   用于获取相对路径的根目录
     * @param ext       要查找的文件扩展名
     * @return 相对于baseDir的相关路径列表
     */
    public static List<String> findFiles(String dir, String baseDir, String ext){
        List<String> list=new ArrayList<String>();
        File rootdir = new File(dir);
        if (!rootdir.exists() || !rootdir.isDirectory()) {
            return list;
        }
        String filePath="";
        String fileExt="";
        String fileName="";
        File[] files = rootdir.listFiles();
        for(File file:files){
            if(file.isDirectory()){
                List<String> tmp=findFiles(file.getAbsolutePath(),baseDir,ext);
                list.addAll(tmp);
            }
            else {
                fileName = file.getName();
                fileExt = fileName.substring(fileName.lastIndexOf('.') + 1);
                if (ext.equals(fileExt)) {
                    filePath = file.getAbsolutePath();
                    filePath=filePath.substring(baseDir.length()+1);
                    list.add(filePath);
                }
            }
        }
        return list;
    }

    /**
     * 向指定文件写入内容(如文件存在，则先删除再创建写入)
     *
     * @param content 保存内容
     * @param path    文件完整路径
     * @return boolean
     */
    public static boolean fileWrite(String content, String path) {
        FileWriter writer = null;
        try {
            File file = new File(path);
            if (file.exists()) {
                file.delete();
            }
            //目录不存在 则创建
            if (!file.getParentFile().exists()) {
                boolean mkdir = file.getParentFile().mkdirs();
                if (!mkdir) {
                    return false;
                }
            }
            file.createNewFile();
            writer = new FileWriter(file);
            if (null != content) {
                writer.write(content);
            }
            writer.flush();
        } catch (IOException e) {
            log.error(GlobalExceptionHandler.getStackTraceInfo(e));
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                } catch (IOException e) {
                    log.error(GlobalExceptionHandler.getStackTraceInfo(e));
                }
            }
        }
        return true;
    }
    /**
     * 追加文件：使用RandomAccessFile
     *
     * @param path    文件追加路径
     * @param content 追加的内容
     * @throws IOException io异常
     */
    public static void additionalContent(String path, String content) throws IOException {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(path, true)));
            out.write(content);
        } catch (Exception e) {
            log.error(GlobalExceptionHandler.getStackTraceInfo(e));
            throw e;
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                log.error(GlobalExceptionHandler.getStackTraceInfo(e));
            }
        }
    }

    /**
     * 插入到指定位置
     *
     * @param filename      文件路径+文件名称
     * @param pos           插入的位置 【首行的话是0】
     * @param insertContent 待插入的数据
     * @return boolean
     * @throws IOException io异常
     */
    public static boolean insert(String filename, int pos, String insertContent) throws IOException {
        //1、参数校验
        File file = new File(filename);
        //获取文件名+后缀名
        String fileName = file.getName().substring(0, file.getName().lastIndexOf(".")).toLowerCase();
        //判断文件是否存在
        if (!(file.exists() && file.isFile())) {
            return false;
        }
        //判断position是否合法
        if ((pos < 0) || (pos > file.length())) {
            return false;
        }
        try {
            //2、创建临时文件
            File tempFile = File.createTempFile(fileName, ".temp");
            //3、用文件输入流、文件输出流对文件进行操作
            FileOutputStream outputStream = new FileOutputStream(tempFile);
            FileInputStream inputStream = new FileInputStream(tempFile);
            //4、创建RandomAccessFile流
            RandomAccessFile rw = new RandomAccessFile(file, "rw");
            //文件指定位置到 pos
            rw.seek(pos);

            int tmp;
            //5、将position位置后的内容写入临时文件
            while ((tmp = rw.read()) != -1) {
                outputStream.write(tmp);
            }
            //6、将追加内容 insertContent 写入 pos 位置
            rw.seek(pos);
            rw.write(insertContent.getBytes());

            //7、将临时文件写回文件，并将创建的流关闭
            while ((tmp = inputStream.read()) != -1) {
                rw.write(tmp);
            }
            rw.close();
            outputStream.close();
            inputStream.close();
            //在退出JVM退出时自动删除
            tempFile.deleteOnExit();
        } catch (IOException e) {
            log.error(GlobalExceptionHandler.getStackTraceInfo(e));
            throw e;
        }
        return true;
    }
    /***
     * 替换指定文件中的指定内容
     *
     * @param filepath 文件路径
     * @param sourceStr 文件需要替换的内容
     * @param targetStr 替换后的内容
     * @return 替换成功返回true，否则返回false
     * @throws FileNotFoundException 文件未找到异常
     */
    public static boolean replaceFileStr(String filepath, String sourceStr, String targetStr) throws FileNotFoundException {
        File file = new File(filepath);
        if (!file.exists()) {
            throw new FileNotFoundException(filepath);
        }
        try {
            // 创建文件输入流
            FileReader fis = new FileReader(filepath);
            // 创建缓冲字符数组
            char[] data = new char[10240];
            int rn = 0;
            StringBuilder sb = new StringBuilder();
            // 错误或者已到达流的末尾前，此方法一直阻塞。读取的字符数，如果已到达流的末尾，则返回 -1
            while ((rn = fis.read(data)) > 0) {
                String str = String.valueOf(data, 0, rn);
                sb.append(str);
            }
            fis.close();
            // 从构建器中生成字符串，并替换搜索文本
            String str = sb.toString().replace(sourceStr, targetStr);
            FileWriter fout = new FileWriter(filepath);
            fout.write(str.toCharArray());
            fout.close();

            return true;
        } catch (FileNotFoundException e) {
            log.error(GlobalExceptionHandler.getStackTraceInfo(e));
            return false;
        } catch (IOException e) {
            log.error(GlobalExceptionHandler.getStackTraceInfo(e));
            return false;
        }

    }

    /***
     * 读取文件指定行
     *
     * @param sourceFile 要读取的文件
     * @param lineNumber 行数
     * @return 读取的内容
     */
    public static String readAppointedLineNo(File sourceFile, int lineNumber)
    {
        String readContent = "";
        FileReader in=null;
        LineNumberReader reader=null;
        try {
            in = new FileReader(sourceFile);
            reader = new LineNumberReader(in);
            String s = "";
            if (lineNumber <= 0 || lineNumber > getTotalLineNum(sourceFile)) {
                reader.close();
                in.close();
                return null;
            }
            int lines = 0;
            while (s != null) {
                lines++;
                s = reader.readLine();
                if ((lines - lineNumber) == 0) {
                    readContent = s;
                    break;
                }
            }
        }catch(Exception e)
        {
            log.error(GlobalExceptionHandler.getStackTraceInfo(e));
        }finally {
            try {
                if(reader!=null) {
                    reader.close();
                }
                if(in!=null) {
                    in.close();
                }
            } catch (IOException e) {
                log.error(GlobalExceptionHandler.getStackTraceInfo(e));
            }
        }
        return readContent;
    }

    /***
     * 文件内容的总行数
     *
     * @param file 文件
     * @return 文件内容的总行数
     */
    public static int getTotalLineNum(File file)  {
        int lines = 0;
        FileReader in=null;
        LineNumberReader reader=null;
        try {
            in = new FileReader(file);
            reader = new LineNumberReader(in);
            String s = reader.readLine();
            while (s != null) {
                lines++;
                s = reader.readLine();
            }
        }catch(Exception e)
        {
            log.error(GlobalExceptionHandler.getStackTraceInfo(e));
        }finally {
            try {
                if(reader!=null) {
                    reader.close();
                }
                if(in!=null) {
                    in.close();
                }
            } catch (IOException e) {
                log.error(GlobalExceptionHandler.getStackTraceInfo(e));
            }
        }
        return lines;
    }
    /***
     * 找到文件内容的行号
     *
     * @param file 文件
     * @param content 文件内容
     * @return 文件内容的行数
     */
    public static int getContentLineNo(File file, String content){
        int lines = 0;
        FileReader in =null;
        LineNumberReader reader =null;
        try {
            in = new FileReader(file);
            reader = new LineNumberReader(in);
            String s = null;
            do {
                lines++;
                s = reader.readLine();
            } while (!content.equals(s));
        }catch(Exception e)
        {
            log.error(GlobalExceptionHandler.getStackTraceInfo(e));
        }finally {
            try {
                if(reader!=null) {
                    reader.close();
                }
                if(in!=null) {
                    in.close();
                }
            } catch (IOException e) {
                log.error(GlobalExceptionHandler.getStackTraceInfo(e));
            }
        }
        return lines;
    }

    /***
     * 返回文件内容的所有行号
     *
     * @param file 文件
     * @param content 文件内容
     * @return 文件内容的行数
     */
    public static List<Integer> getAllLineNo(File file, String content){
        FileReader in =null;
        LineNumberReader reader =null;
        List<Integer> list = new ArrayList<Integer>();
        try {
            in = new FileReader(file);
            reader = new LineNumberReader(in);
            int totalLine = getTotalLineNum(file);
            String s = null;
            Integer lines = 0;
            do {
                lines++;
                s = reader.readLine();
                if (s.contains(content)) {
                    list.add(lines);
                }
            } while (totalLine > lines);
        }catch(Exception e)
        {
            log.error(GlobalExceptionHandler.getStackTraceInfo(e));
        }finally {
            try {
                if(reader!=null) {
                    reader.close();
                }
                if(in!=null) {
                    in.close();
                }
            } catch (IOException e) {
                log.error(GlobalExceptionHandler.getStackTraceInfo(e));
            }
        }
        return list;
    }

    /**
     * 读取文本文件的内容
     *
     * @param isIgnoreEmptyLine 是否忽略空行
     * @param filePath 文件路径
     * @return 返回读取的文件内容
     * @throws FileNotFoundException 文件未找到异常
     */
    public static String readTextFile(String filePath,boolean isIgnoreEmptyLine) throws FileNotFoundException {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new FileNotFoundException(filePath);
        }
        FileReader in =null;
        LineNumberReader reader =null;
        StringBuilder sb=new StringBuilder();
        try {
            in = new FileReader(filePath);
            reader = new LineNumberReader(in);
            String s = null;
            do {
                s = reader.readLine();
                if(s!=null){
                    if(s.trim().equals("")){
                        if(!isIgnoreEmptyLine){
                            sb.append(s + "\n");
                        }
                    }else{
                        sb.append(s + "\n");
                    }
                }
            } while (s!=null);
        }catch(Exception e)
        {
            log.error(GlobalExceptionHandler.getStackTraceInfo(e));
        }finally {
            try {
                if(reader!=null) {
                    reader.close();
                }
                if(in!=null) {
                    in.close();
                }
            } catch (IOException e) {
                log.error(GlobalExceptionHandler.getStackTraceInfo(e));
            }
        }
        return sb.toString();
    }

    /**
     * 输出指定文件的byte数组
     *
     * @param filePath 文件路径
     * @param os       输出流
     */
    public static void writeBytes(String filePath, OutputStream os) {
        FileInputStream fis = null;
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new FileNotFoundException(filePath);
            }
            fis = new FileInputStream(file);
            byte[] b = new byte[1024];
            int length;
            while ((length = fis.read(b)) > 0) {
                os.write(b, 0, length);
            }
        } catch (IOException e) {
            log.error(GlobalExceptionHandler.getStackTraceInfo(e));
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e1) {
                    log.error(GlobalExceptionHandler.getStackTraceInfo(e1));
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    log.error(GlobalExceptionHandler.getStackTraceInfo(e1));
                }
            }
        }
    }

    /**
    * 读取文件并转为byte数组
    *
    * @param filename 文件名
    * @return byte[]
    * @throws FileNotFoundException 文件未找到异常
    */
    public static byte[] toByteArray(String filename) throws FileNotFoundException {

        File f = new File(filename);
        if (!f.exists()) {
            throw new FileNotFoundException(filename);
        }
        FileChannel channel = null;
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(f);
            channel = fs.getChannel();
            ByteBuffer byteBuffer = ByteBuffer.allocate((int) channel.size());
            while ((channel.read(byteBuffer)) > 0) {
                // do nothing
                // System.out.println("reading");
            }
            return byteBuffer.array();
        } catch (IOException e) {
            throw new FileException("文件读取错误");
        } finally {
            try {
                channel.close();
            } catch (IOException e) {
                throw new FileException("文件读取错误");
            }
            try {
                fs.close();
            } catch (IOException e) {
                throw new FileException("文件读取错误");
            }
        }
    }

    /**
     * 文件名称验证
     *
     * @param filename 文件名称
     * @return true 正常 false 非法
     */
    public static boolean isValidFilename(String filename) {
        return filename.matches(FILENAME_PATTERN);
    }

    /**
     * 检查文件是否可下载
     *
     * @param resource 需要下载的文件
     * @return true 正常 false 非法
     */
    public static boolean checkAllowDownload(String resource)
    {
        // 禁止目录上跳级别
        if (StringUtils.contains(resource, ".."))
        {
            return false;
        }

        // 检查允许下载的文件规则
        return ArrayUtils.contains(MimeTypeUtils.DEFAULT_ALLOWED_EXTENSION, getFileType(resource));

    }

    /**
     * 下载文件名重新编码
     *
     * @param request 请求对象
     * @param fileName 文件名
     * @return 编码后的文件名
     * @throws UnsupportedEncodingException 不支持的编码异常
     */
    public static String setFileDownloadHeader(HttpServletRequest request, String fileName) throws UnsupportedEncodingException
    {
        final String agent = request.getHeader("USER-AGENT");
        String filename = fileName;
        if (agent.contains("MSIE"))
        {
            // IE浏览器
            filename = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString());
            filename = filename.replace("+", " ");
        }
        else if (agent.contains("Firefox"))
        {
            // 火狐浏览器
            filename = new String(fileName.getBytes(), "ISO8859-1");
        }
        else if (agent.contains("Chrome"))
        {
            // google浏览器
            filename = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString());
        }
        else
        {
            // 其它浏览器
            filename = URLEncoder.encode(filename, StandardCharsets.UTF_8.toString());
        }
        return filename;
    }

    /**
     * 从文件路径中返回文件名
     *
     * @param filePath 文件路径
     * @return 文件名
     */
    public static String getFileName(String filePath)
    {
        if (null == filePath)
        {
            return null;
        }
        int len = filePath.length();
        if (0 == len)
        {
            return filePath;
        }
        if (isFileSeparator(filePath.charAt(len - 1)))
        {
            // 以分隔符结尾的去掉结尾分隔符
            len--;
        }

        int begin = 0;
        char c;
        for (int i = len - 1; i > -1; i--)
        {
            c = filePath.charAt(i);
            if (isFileSeparator(c))
            {
                // 查找最后一个路径分隔符（/或者\）
                begin = i + 1;
                break;
            }
        }

        return filePath.substring(begin, len);
    }

    /**
     * 获取文件类型
     * <p>
     * 例如: ruoyi.txt, 返回: txt
     *
     * @param file 文件名
     * @return 后缀（不含".")
     */
    public static String getFileType(File file)
    {
        if (null == file)
        {
            return org.apache.commons.lang3.StringUtils.EMPTY;
        }
        return getFileType(file.getName());
    }

    /**
     * 获取文件类型
     * <p>
     * 例如: ruoyi.txt, 返回: txt
     *
     * @param fileName 文件名
     * @return 后缀（不含".")
     */
    public static String getFileType(String fileName)
    {
        int separatorIndex = fileName.lastIndexOf('.');
        if (separatorIndex < 0)
        {
            return "";
        }
        return fileName.substring(separatorIndex + 1).toLowerCase();
    }

    /**
     * 是否为Windows或者Linux（Unix）文件分隔符<br>
     * Windows平台下分隔符为\，Linux（Unix）为/
     *
     * @param c 字符
     * @return 是否为Windows或者Linux（Unix）文件分隔符
     */
    public static boolean isFileSeparator(char c)
    {
        return SLASH == c || BACKSLASH == c;
    }

    /**
     * <p>下载文件名重新编码
     * <p>解决中文名乱码问题，设置头为：Content-Disposition: attachment; filename=  ;filename*=utf-8''
     * @param response 响应对象
     * @param realFileName 真实文件名
     */
    public static void setAttachmentResponseHeader(HttpServletResponse response, String realFileName) throws UnsupportedEncodingException {
        String percentEncodedFileName = percentEncode(realFileName);

        String contentDispositionValue = "attachment; filename=" +
                percentEncodedFileName +
                ";" +
                "filename*=" +
                "utf-8''" +
                percentEncodedFileName;

        response.setHeader("Content-disposition", contentDispositionValue);
    }

    /**
     * 百分号编码工具方法
     *
     * @param s 需要百分号编码的字符串
     * @return 百分号编码后的字符串
     */
    public static String percentEncode(String s) throws UnsupportedEncodingException {
        String encode = URLEncoder.encode(s, StandardCharsets.UTF_8.toString());
        return encode.replaceAll("\\+", "%20");
    }


}

package com.example.common.exception;

import com.example.common.core.R;
import com.example.common.exception.file.FileException;
import com.example.common.util.ServletUtils;
import com.example.common.util.StringUtils;
import com.fasterxml.jackson.core.JsonParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常处理器,@ControllerAdvice + @ExceptionHandle可以处理 除404以外的运行异常<br>
 * RestControllerAdvice可拦截所有使用@RequestMapping方法
 * <br>
 * @author lihuanzhe
 * @version 1.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
    * 处理响应异常
    *
    * @param e 响应异常
    * @return R
     */
    @ExceptionHandler({ResponseStatusException.class})
    public R handleErrorResponseException(ResponseStatusException e) {
        String error = StringUtils.format("code:{}, message:{}, path:{}, stack:{}",
                HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), ServletUtils.getRequest().getRequestURI(),getStackTraceInfo(e));
        log.error("Response Exception，{}", error);
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "响应异常:" + e.getMessage(),ServletUtils.getRequest().getRequestURI());
    }

    /**
    * 请求方式不支持
    *
    * @param e 请求方式不支持异常
    * @return R
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public R handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        String error = StringUtils.format("code:{}, message:{}, path:{}",
                HttpStatus.METHOD_NOT_ALLOWED.value(), "请求的方法不被允许", ServletUtils.getRequest().getRequestURI());
        log.error("Not Supported Exception，{}", error);
        return R.error(HttpStatus.METHOD_NOT_ALLOWED.value(),"本接口不支持'" + e.getMethod() + "'方法请求",ServletUtils.getRequest().getRequestURI());
    }

    /**
    *  拦截未知的运行时异常
    *
    * @param e 运行时异常
    * @return R
     */
    @ExceptionHandler(RuntimeException.class)
    public R handleRuntimeException(RuntimeException e) {
        String error = StringUtils.format("code:{}, message:{}, path:{}, stack:{}",
                HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), ServletUtils.getRequest().getRequestURI(),getStackTraceInfo(e));
        log.error("Runtime Exception，{}", error);

        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "运行时异常:" + e.getMessage(),ServletUtils.getRequest().getRequestURI());
    }

    /**
     *  处理空指针异常
     *
     * @param e 空指针异常
     * @return R
     */
    @ExceptionHandler(NullPointerException.class)
    public R handleIOException(NullPointerException e) {
        String error = StringUtils.format("code:{}, message:{}, path:{}, stack:{}",
                HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), ServletUtils.getRequest().getRequestURI(),getStackTraceInfo(e));
        log.error("NullPointer Exception，{}", error);

        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "空指针异常:" + e.getMessage(),ServletUtils.getRequest().getRequestURI());
    }

    /**
    *  处理IO异常
    *
    * @param e IO异常
    * @return R
     */
    @ExceptionHandler(IOException.class)
    public R handleIOException(IOException e) {
        String error = StringUtils.format("code:{}, message:{}, path:{}, stack:{}",
                HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), ServletUtils.getRequest().getRequestURI(),getStackTraceInfo(e));
        log.error("IO Exception，{}", error);

        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "IO异常:" + e.getMessage(),ServletUtils.getRequest().getRequestURI());
    }

    /**
    * 处理json解析异常
    *
    * @param e json解析异常
    * @return R
     */
    @ExceptionHandler(JsonParseException.class)
    public R handleJsonParseException(JsonParseException e) {
        String error = StringUtils.format("code:{}, message:{}, path:{}, stack:{}",
                HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), ServletUtils.getRequest().getRequestURI(),getStackTraceInfo(e));
        log.error("JsonParse Exception，{}", error);

        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "JSON解析异常:" + e.getMessage(),ServletUtils.getRequest().getRequestURI());
    }

    /**
    * 处理json异常
    *
    * @param e json异常
    * @return R
     */
    @ExceptionHandler(JSONException.class)
    public R handleJSONException(JSONException e) {
        String error = StringUtils.format("code:{}, message:{}, path:{}, stack:{}",
                HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), ServletUtils.getRequest().getRequestURI(),getStackTraceInfo(e));
        log.error("Json Exception，{}", error);

        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "JSON异常:" + e.getMessage(),ServletUtils.getRequest().getRequestURI());
    }

    /**
    * 处理自定义业务异常
    *
    * @param e 自定义业务异常
    * @return R
     */
    @ExceptionHandler(CustomException.class)
    public R handleBusinessException(CustomException e) {
        String error = StringUtils.format("code:{}, message:{}, path:{}",
                e.getCode(), e.getMessage(), ServletUtils.getRequest().getRequestURI());
        log.error("Custom Exception，{}", error);
        return R.error(e.getCode(), e.getMessage(),ServletUtils.getRequest().getRequestURI());
    }

    /**
    * 文件异常
    *
    * @param e 文件异常
    * @return R
     */
    @ExceptionHandler(FileException.class)
    public R handleFileException(FileException e) {
        String error = StringUtils.format("code:{}, message:{}, path:{}",
                e.getCode(), e.getMessage(), ServletUtils.getRequest().getRequestURI());
        log.error("File Exception，{}", error);
        return R.error(e.getCode(),e.getMessage(),ServletUtils.getRequest().getRequestURI());
    }


    /**
     * 功能描述: 处理未知exception异常
     *
     * @param e 异常
     * @return R
     */
    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        String error = StringUtils.format("code:{}, message:{}, path:{}, stack:{}",
                HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(), ServletUtils.getRequest().getRequestURI(),
                getStackTraceInfo(e));
        log.error("Exception，{}", error);
        return R.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage(),ServletUtils.getRequest().getRequestURI());
    }

    /**
    * 获取堆栈轨迹(StackTrace)
    *
    * @param error 抛出的异常
    * @return java.lang.String
     */
    public static String getStackTraceInfo(Throwable error) {
        StringWriter stackTrace = new StringWriter();
        error.printStackTrace(new PrintWriter(stackTrace));
        stackTrace.flush();
        return stackTrace.toString();
    }
}

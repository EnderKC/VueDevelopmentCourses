package com.example.common.exception;

import com.example.common.core.R;
import com.example.common.util.ServletUtils;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * 处理404找不到页面错误
 * <br>
 * @author lihuanzhe
 * @version 1.0
 */
@RestController
public class GlobalErrorController implements ErrorController {
    private static final String ERROR_PATH = "/error";

    /**
    * 处理404异常
    * @return R
    */
    @RequestMapping(value = {ERROR_PATH}, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public R handleError() {
        return R.error(HttpStatus.NOT_FOUND.value(),"要访问的接口不存在", ServletUtils.getRequest().getRequestURI());
    }

    /**
    * 返回错误路径
    *
    * @return java.lang.String
    */
    public String getErrorPath() {
        return ERROR_PATH;
    }
}

package top.qwwq.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.qwwq.Utils.ResponseVo;
import top.qwwq.exception.UnauthorizedException;

@Slf4j
@RestControllerAdvice
public class ExceptionApi {
    /** 捕捉 shiro 的异常 */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ResponseVo<String> handle401(ShiroException e) {
        return new ResponseVo<>(401, e.getMessage());
    }

    /** 捕捉UnauthorizedException */
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseVo<String> handle401() {
        return new ResponseVo<>(401, "Unauthorized");
    }

    /** 捕捉其他所有异常 */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseVo<String> globalException(HttpServletRequest request, Throwable ex) {
        log.error("Global exception handler caught an exception:", ex);
        return new ResponseVo<>(getStatus(request).value(), ex.getMessage());
    }


    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }

}

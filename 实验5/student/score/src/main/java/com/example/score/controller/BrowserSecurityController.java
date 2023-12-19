package com.example.score.controller;

import com.example.common.core.R;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 浏览器安全管理接口
 * <br>
 *
 * @author lihuanzhe
 * @version 1.0
 */

@RestController
public class BrowserSecurityController {

    @GetMapping("/authentication/require")
    public R requireAuthentication(){
        return R.error(HttpStatus.UNAUTHORIZED.value(),"authentication required").put("data","/login");
    }
    /**
     * session失效时返回给前端的信息
     * @return  R
     */
    @GetMapping("/session/invalid")
    public R sessionInvalid(){
        return R.error(HttpStatus.UNAUTHORIZED.value(),"session invalid").put("data","/login");
    }


}

package top.qwwq.controller;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.qwwq.pojo.User;
import top.qwwq.service.SystemService;
import top.qwwq.utils.ResponseVo;

@RestController
public class SystemController {
    SystemService systemService;
    public SystemController(SystemService systemService) {
        this.systemService = systemService;
    }

    /**
     * 处理非法请求
     */
    @GetMapping("/unauthorized")
    public ResponseVo<String> unauthorized() {
        return new ResponseVo<>(-1, "Token失效请重新登录!");
    }

    /*
     * 用户的登录接口
     * */
    @PostMapping("/login")
    public ResponseVo<JSONObject> login(@RequestBody User user) {
        return systemService.login(user.getUserName(),user.getUserPassword());
    }

    /*
     * 检验token是否有效
     */
    @PostMapping("/checkToken")
    public ResponseVo<String> checkToken(@RequestBody JSONObject requestBody){
        String token = requestBody.getString("token");
        return systemService.checkToken(token);
    }
}

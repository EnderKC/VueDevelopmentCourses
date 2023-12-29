package top.qwwq.controller;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import top.qwwq.pojo.User;
import top.qwwq.service.SystemService;
import top.qwwq.utils.ResponseVo;

@Slf4j
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

    /**
     * 用户的注册接口
     */
    @PostMapping("/register")
    public ResponseVo<JSONObject> register(@RequestBody JSONObject jsonObject){
        String userName = jsonObject.getString("userName");
        String userPassword = jsonObject.getString("userPassword");
        String userPassword2 = jsonObject.getString("userPassword2");
        String userEmail = jsonObject.getString("userEmail");
        String verificationCode = jsonObject.getString("verificationCode");
        return systemService.register(userName,userPassword,userPassword2,userEmail,verificationCode);
    }

    /*
     * 需要的数据：
     *   to: 邮箱地址
     * */
    @PostMapping("/sendVerificationCode")
    public ResponseVo<String> sendVerificationCode(@RequestBody JSONObject requestBody) {
        String to = requestBody.getString("userEmail");
        log.info(to);
        // 在这里继续处理发送验证码的逻辑
        return systemService.sendVerificationCode(to);
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

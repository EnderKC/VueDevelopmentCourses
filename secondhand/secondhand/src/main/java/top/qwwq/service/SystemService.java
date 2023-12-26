package top.qwwq.service;

import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import top.qwwq.pojo.User;
import top.qwwq.utils.ResponseVo;

public interface SystemService {
    /*
     * 用户登录
     * */
    ResponseVo<JSONObject> login (String username, String password);
    /*
     * 从token中获取用户信息
     * */
    User getUserFromToken(ServletRequest request, ServletResponse response);
    /*
     * 利用Jwt发送邮箱验证码
     * */
    ResponseVo<String> sendVerificationCode(String email);
    /*
     * 检查验证码是否正确
     * */
    ResponseVo<String> checkVerificationCode(String verificationCode, String email);

    /*
      检验token是否有效
     */
    ResponseVo<String> checkToken(String token);
}

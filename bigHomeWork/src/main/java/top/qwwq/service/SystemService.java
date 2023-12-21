package top.qwwq.service;


import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import top.qwwq.Utils.ResponseVo;
import top.qwwq.pojo.TUser;

public interface SystemService {
    /*
    * 用户登录
    * */
    ResponseVo<JSONObject> login(String username, String password);

    /*
    * 用户注册
    * */
    ResponseVo<JSONObject> register(TUser tUser);

    /*
    * 从token中获取用户信息
    * */
    TUser getUserFromToken(ServletRequest request, ServletResponse response);
    /*
      检验token是否有效
     */
    ResponseVo<String> checkToken(String token);


}

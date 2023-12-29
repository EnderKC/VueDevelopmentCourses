package top.qwwq.controller;

import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import top.qwwq.pojo.User;
import top.qwwq.pojo.UserInfo;
import top.qwwq.service.IUserService;
import top.qwwq.service.SystemService;
import top.qwwq.utils.ResponseVo;

/**
 * <p>
 * 用户信息表 前端控制器
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-26
 */
@Slf4j
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    IUserService userService;
    SystemService service;
    public UserInfoController(IUserService userService, SystemService service) {
        this.userService = userService;
        this.service = service;
    }

    // 修改用户信息
    @PostMapping("/update")
    public ResponseVo<JSONObject> updateUserInfo(@RequestBody UserInfo userInfo,ServletRequest request, ServletResponse response){
        User user = service.getUserFromToken(request, response);
        userInfo.setUserId(user.getUserId());
        log.info(userInfo.toString());
        return userService.updateUserInfo(userInfo);
    }

    // 获取用户信息
    @PostMapping("/getInfo")
    public ResponseVo<JSONObject> getInfo(ServletRequest request, ServletResponse response){
        User user = service.getUserFromToken(request, response);
        return userService.getUserInfo(user.getUserId());
    }

}

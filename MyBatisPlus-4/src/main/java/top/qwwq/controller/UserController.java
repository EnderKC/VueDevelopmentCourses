package top.qwwq.controller;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.qwwq.pojo.User;
import top.qwwq.service.UserService;
import top.qwwq.utils.ResponseVo;

@RestController
public class UserController {
    UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/insertUser")
    public ResponseVo<String> insertUser(@RequestBody User user){
        return  userService.insertUser(user);
    }

    @RequestMapping("/deleteUser")
    public ResponseVo<String> deleteUser(@RequestBody JSONObject request){
        Long userID = request.getLong("userID");
        return  userService.deleteUser(userID);
    }

    @RequestMapping("/updateUser")
    public ResponseVo<String> updateUser(@RequestBody User user){
        return  userService.updateUser(user);
    }

    @RequestMapping("/selectUser")
    public ResponseVo<User> selectUser(@RequestBody JSONObject request){
        Long userID = request.getLong("userID");
        return  userService.selectUser(userID);
    }
}

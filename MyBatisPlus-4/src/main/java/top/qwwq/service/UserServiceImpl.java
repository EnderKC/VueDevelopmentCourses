package top.qwwq.service;

import org.springframework.stereotype.Service;
import top.qwwq.mapper.UserMapper;
import top.qwwq.pojo.User;
import top.qwwq.utils.ResponseVo;

@Service
public class UserServiceImpl implements UserService {
    UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;

    }

    // 增加用户
    public ResponseVo<String> insertUser(User user) {
        userMapper.insertUser(user);
        return new ResponseVo<>(0, "success");
    }

    // 删除用户
    public ResponseVo<String> deleteUser(Long userID) {
        userMapper.deleteUser(userID);
        return new ResponseVo<>(0, "success");
    }

    // 修改用户
    public ResponseVo<String> updateUser(User user) {
        userMapper.updateUser(user);
        return new ResponseVo<>(0, "success");
    }

    // 查询用户
    public ResponseVo<User> selectUser(Long userID) {
        User user = userMapper.selectUser(userID);
        return new ResponseVo<>(0, "success", user);
    }

}

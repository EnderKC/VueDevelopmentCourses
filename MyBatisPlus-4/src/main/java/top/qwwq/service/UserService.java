package top.qwwq.service;

import top.qwwq.mapper.UserMapper;
import top.qwwq.pojo.User;
import top.qwwq.utils.ResponseVo;

public interface UserService {
    public ResponseVo<String> insertUser(User user);

    public ResponseVo<String> deleteUser(Long userID);

    public ResponseVo<String> updateUser(User user);

    public ResponseVo<User> selectUser(Long userID);
}

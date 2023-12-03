package top.qwwq.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.qwwq.pojo.User;

@Mapper
public interface UserMapper {
    /*
     *  增加用户
    */
    void insertUser(User user);
    /*
        *  删除用户
     */
    void deleteUser(Long userID);
    /*
        *  修改用户
     */
    void updateUser(User user);
    /*
        *  查询用户
     */
    User selectUser(Long userID);


}

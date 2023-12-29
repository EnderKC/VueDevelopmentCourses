package top.qwwq.service;

import com.alibaba.fastjson2.JSONObject;
import top.qwwq.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import top.qwwq.pojo.UserInfo;
import top.qwwq.utils.ResponseVo;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-26
 */
public interface IUserService extends IService<User> {
    /*
    新增用户信息，用于注册的时候自动新增空白的用户信息，以便用户修改
     */
    boolean insertUserInfo(String userID);
    /*
    修改用户信息
     */
    ResponseVo<JSONObject> updateUserInfo(UserInfo userInfo);

    /**
     * 根据用户ID获取用户信息
     */
    ResponseVo<JSONObject> getUserInfo(String userID);


}

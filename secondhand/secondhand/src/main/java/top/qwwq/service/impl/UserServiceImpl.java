package top.qwwq.service.impl;

import com.alibaba.fastjson2.JSONObject;
import top.qwwq.mapper.UserInfoMapper;
import top.qwwq.pojo.User;
import top.qwwq.mapper.UserMapper;
import top.qwwq.pojo.UserInfo;
import top.qwwq.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.qwwq.utils.ResponseVo;

import static org.apache.shiro.web.filter.mgt.DefaultFilter.user;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-26
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    UserMapper userMapper;
    UserInfoMapper userInfoMapper;
    public UserServiceImpl(UserMapper userMapper,UserInfoMapper userInfoMapper) {
        this.userMapper = userMapper;
        this.userInfoMapper = userInfoMapper;
    }

    @Override
    public boolean insertUserInfo(String userID) {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userID);
        userInfoMapper.insert(userInfo);
        return true;
    }

    @Override
    public ResponseVo<JSONObject> updateUserInfo(UserInfo userInfo) {
        try{
            userInfoMapper.updateById(userInfo);
            return new ResponseVo<>(0,"修改成功");
        }catch (Exception e){
            return new ResponseVo<>(-1,"修改失败");
        }
    }

    @Override
    public ResponseVo<JSONObject> getUserInfo(String userID) {
        UserInfo userInfo = userInfoMapper.selectById(userID);
        if (userInfo != null){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("userInfo",userInfo);
            return new ResponseVo<>(0,"查询成功",jsonObject);
        }
        return new ResponseVo<>(-1,"查询失败");
    }
}

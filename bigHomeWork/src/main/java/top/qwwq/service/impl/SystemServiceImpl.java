package top.qwwq.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.qwwq.Utils.GetUUID;
import top.qwwq.Utils.JwtUtil;
import top.qwwq.Utils.RedisUtil;
import top.qwwq.Utils.ResponseVo;
import top.qwwq.mapper.TUserMapper;
import top.qwwq.pojo.TUser;
import top.qwwq.service.SystemService;

@Service
@Slf4j
public class SystemServiceImpl implements SystemService {

    TUserMapper tUserMapper;
    RedisUtil redisUtil;
    public SystemServiceImpl(TUserMapper tUserMapper, RedisUtil redisUtil) {
        this.tUserMapper = tUserMapper;
        this.redisUtil = redisUtil;
    }

    @Override
    public ResponseVo<JSONObject> login(String username, String password) {
        // 处理对比密码
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        TUser tUser = tUserMapper.selectOne(wrapper);
        if(tUser != null){
            // TODO: 省略一下加密的步骤
            log.info("student.getPassword() = " + tUser.getPassword());
            if(tUser.getPassword().equals(password)){
                String token = getToken(tUser);
                JSONObject tokenJson = new JSONObject();
                tokenJson.put("token",token);
                return  new ResponseVo<>(0, "登录成功", tokenJson);
            }
        }
        return new ResponseVo<>(-1, "登录失败");
    }

    @Override
    public ResponseVo<JSONObject> register(TUser tUser) {
        QueryWrapper<TUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username", tUser.getUsername());
        if (tUserMapper.selectOne(wrapper) != null){
            return new ResponseVo<>(-1,"用户名已存在");
        }
        // 如果没有定义id，就自动生成一个uuid
        if (tUser.getId() == null){
            tUser.setId(GetUUID.getUUID());
        }
        try{
            int insert = tUserMapper.insert(tUser);
        }catch (Exception e){
            return new ResponseVo<>(-1,"注册失败");
        }
        return new ResponseVo<>(0,"注册成功");
    }

    private String getToken(TUser tUser){
        // 生成token
        String token = JwtUtil.createToken(tUser);
        // 为了过期续签，将token存入redis，并设置超时时间
        redisUtil.set(token, token, JwtUtil.getExpireTime());
        return token;
    }

    @Override
    public TUser getUserFromToken(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");
        String id = JwtUtil.parseTokenId(authorization);
        return tUserMapper.selectById(id);
    }

    @Override
    public ResponseVo<String> checkToken(String token) {
        if (JwtUtil.isVerify(token)){
            return new ResponseVo<>(0,"token有效");
        }
        return new ResponseVo<>(-1,"token无效");
    }
}
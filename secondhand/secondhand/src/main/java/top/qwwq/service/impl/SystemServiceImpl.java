package top.qwwq.service.impl;

import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.qwwq.mapper.UserMapper;
import top.qwwq.pojo.User;
import top.qwwq.service.EmailService;
import top.qwwq.service.IUserService;
import top.qwwq.service.SystemService;
import top.qwwq.utils.GetUUID;
import top.qwwq.utils.JwtUtil;
import top.qwwq.utils.RedisUtil;
import top.qwwq.utils.ResponseVo;

@Service
@Slf4j
public class SystemServiceImpl implements SystemService {

    UserMapper userMapper;
    RedisUtil redisUtil;
    EmailService emailService;
    IUserService userService;
    public SystemServiceImpl(UserMapper userMapper,RedisUtil redisUtil,EmailService emailService,IUserService userService) {
        this.userMapper = userMapper;
        this.redisUtil = redisUtil;
        this.emailService = emailService;
        this.userService = userService;
    }

    @Override
    public ResponseVo<JSONObject> login(String username, String password) {
        // 处理对比密码
//        Student student = studentMapper.queryByqqNum(qqNum);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name",username);
        User user = userMapper.selectOne(userQueryWrapper);
        if(user != null){
            // TODO: 省略一下加密的步骤
            log.info("student.getPassword() = " + user.getUserPassword());
            if(user.getUserPassword().equals(password)){
                String token = getToken(user);
                JSONObject tokenJson = new JSONObject();
                tokenJson.put("token",token);
                return  new ResponseVo<>(0, "登录成功", tokenJson);
            }
        }
        return new ResponseVo<>(-1, "登录失败");
    }

    @Override
    public ResponseVo<JSONObject> register(String username, String password, String password2, String email,String verificationCode) {
        // 检查验证码是否正确
        String redisVerificationCode = (String) redisUtil.get(email);
        if(redisVerificationCode == null){
            return new ResponseVo<>(-1,"验证码已过期，请重新发送");
        }
        if(!redisVerificationCode.equals(verificationCode)){
            return new ResponseVo<>(-1,"验证码错误",null);
        }
        // 检查密码是否一致
        if(!password.equals(password2)){
            return new ResponseVo<>(-1,"两次密码不一致");
        }
        // 检查用户名是否重复
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("user_name",username);
        User user = userMapper.selectOne(userQueryWrapper);
        if(user != null){
            return new ResponseVo<>(-1,"用户名已存在");
        }
        // 检查邮箱是否重复
        userQueryWrapper.clear();
        userQueryWrapper.eq("user_email",email);
        user = userMapper.selectOne(userQueryWrapper);
        if(user != null){
            return new ResponseVo<>(-1,"邮箱已存在");
        }
        if(redisVerificationCode.equals(verificationCode)){
            // 保存用户信息
            user = new User();
            user.setUserId(GetUUID.getUUID());
            user.setUserName(username);
            user.setUserPassword(password);
            user.setUserEmail(email);
            user.setUserRole(0);
            userMapper.insert(user);
            // 创建空的用户信息
            userService.insertUserInfo(user.getUserId());
            return new ResponseVo<>(0,"注册成功",null);
        }
        return new ResponseVo<>(-1,"验证码错误",null);
    }

    private String getToken(User user){
        // 生成token
        String token = JwtUtil.createToken(user);
        // 为了过期续签，将token存入redis，并设置超时时间
        redisUtil.set(token, token, JwtUtil.getExpireTime());
        return token;
    }

    @Override
    public User getUserFromToken(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");
        String ID = JwtUtil.parseTokenId(authorization);
        return userMapper.selectById(ID);
    }

    /**
     * @param email 要发送的邮箱
     * @return 发送的结果
     */
    @Override
    public ResponseVo<String> sendVerificationCode(String email) {
        try{
            // 检查上次发送的验证码是否过期
//            if(redisUtil.hasKey(email)){
//                return new ResponseVo<>(-1,"验证码未过期，请稍后再试");
//            }
            // 发送验证码
            String verificationCode = emailService.sendVerificationCode(email);
            // 存入redis（过期时间 5 分钟）
            redisUtil.set(email,verificationCode,60*5);
            // 合成JetToken
            String token = JwtUtil.createTokenEmail(email);
            return new ResponseVo<>(0,"发送成功",token);
        }catch (Exception e){
            log.warn(e.getMessage());
            return new ResponseVo<>(-1,"发送失败");
        }
    }

    /**
     * @param verificationCode 前端发来的验证码
     * @param email 前端发来的邮箱
     * @return 验证结果
     */
    @Override
    public ResponseVo<String> checkVerificationCode(String verificationCode, String email) {
        // 获取redis中的验证码
        String redisVerificationCode = (String) redisUtil.get(email);
        if(redisVerificationCode == null){
            return new ResponseVo<>(-1,"验证码已过期，请重新发送");
        }
        // 对比验证码是否正确
        if(redisVerificationCode.equals(verificationCode)){
            return new ResponseVo<>(0,"验证成功");
        }
        return new ResponseVo<>(-1,"验证码错误");
    }

    @Override
    public ResponseVo<String> checkToken(String token) {
        if (JwtUtil.isVerify(token)){
            return new ResponseVo<>(0,"token有效");
        }
        return new ResponseVo<>(-1,"token无效");
    }
}

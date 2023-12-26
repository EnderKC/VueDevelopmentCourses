package top.qwwq.service.impl;

import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.qwwq.mapper.UserMapper;
import top.qwwq.utils.RedisUtil;
import top.qwwq.utils.ResponseVo;

@Service
@Slf4j
public class SystemServiceImpl {

    UserMapper userMapper;
    RedisUtil redisUtil;
    EmailService emailService;
    public SystemServiceImpl(StudentMapper studentMapper,RedisUtil redisUtil,EmailService emailService) {
        this.studentMapper = studentMapper;
        this.redisUtil = redisUtil;
        this.emailService = emailService;
    }

    @Override
    public ResponseVo<JSONObject> login(String qqNum, String password) {
        // 处理对比密码
        Student student = studentMapper.queryByqqNum(qqNum);
        if(student != null){
            // TODO: 省略一下加密的步骤
            log.info("student.getPassword() = " + student.getPassword());
            if(student.getPassword().equals(password)){
                String token = getToken(student);
                JSONObject tokenJson = new JSONObject();
                tokenJson.put("token",token);
                return  new ResponseVo<>(0, "登录成功", tokenJson);
            }
        }
        return new ResponseVo<>(-1, "登录失败");
    }

    private String getToken(Student student){
        // 生成token
        String token = JwtUtil.createToken(student);
        // 为了过期续签，将token存入redis，并设置超时时间
        redisUtil.set(token, token, JwtUtil.getExpireTime());
        return token;
    }

    @Override
    public Student getUserFromToken(ServletRequest request, ServletResponse response) {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String authorization = httpServletRequest.getHeader("Authorization");
        String studentID = JwtUtil.parseTokenStuId(authorization);
        return studentMapper.queryByStudentID(studentID);
    }

    /**
     * @param email 要发送的邮箱
     * @return 发送的结果
     */
    @Override
    public ResponseVo<String> sendVerificationCode(String email) {
        try{
            // 检查上次发送的验证码是否过期
            if(redisUtil.hasKey(email)){
                return new ResponseVo<>(-1,"验证码未过期，请稍后再试");
            }
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

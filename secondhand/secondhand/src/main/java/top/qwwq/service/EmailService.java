package top.qwwq.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import top.qwwq.service.util.GetVerification;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final GetVerification getVerification;
    public EmailService(JavaMailSender mailSender, GetVerification getVerification) {
        this.mailSender = mailSender;
        this.getVerification = getVerification;
    }

    public void sendSimpleMessage(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("enderkc@qq.com");
        // 收件人
        message.setTo(to);
        // 主题
        message.setSubject(subject);
        // 内容
        message.setText(text);
        mailSender.send(message);
    }

    // 发送验证码
    public String sendVerificationCode(String to) {
        String verificationCode = getVerification.getVerification();
        String msg = "您的验证码为"+verificationCode+"，请在5分钟内使用，不要泄露给他人！";
        sendSimpleMessage(to,"【网络情缘一线牵】验证码",msg);

        return verificationCode;
    }
}


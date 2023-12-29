package top.qwwq.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import top.qwwq.utils.GetVerification;

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

    // 有人下单，发送邮件提醒
    public void sendOrderMessage(String to, String goodName) {
        String subject = "【网络情缘一线牵】待发货通知";
        String text = "有人下单了您的【"+goodName+"】，请及时发货！\n如果不是您本人操作，请忽略此邮件。";
        sendSimpleMessage(to, subject, text);
    }

    // 卖家发货，提醒卖家收获
    public void sendDeliverMessage(String to, String goodName) {
        String subject = "【网络情缘一线牵】待收货通知";
        String text = "您购买的【"+goodName+"】已发货，请注意查收！\n如果不是您本人操作，请忽略此邮件。";
        sendSimpleMessage(to, subject, text);
    }
}


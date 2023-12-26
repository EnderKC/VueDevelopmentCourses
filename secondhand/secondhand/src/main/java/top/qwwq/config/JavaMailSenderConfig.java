package top.qwwq.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class JavaMailSenderConfig {
    // MailSender 配置类
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // 邮箱服务器地址
        mailSender.setHost("smtp.qq.com");
        // 邮箱服务器端口
        mailSender.setPort(465);
        // 邮箱账号
        mailSender.setUsername("enderkc@qq.com");
        // 密码
        mailSender.setPassword("nfkldjfsqplkcbce");
        // SL/TLS配置
        mailSender.getJavaMailProperties().put("mail.smtp.ssl.enable", true);
//        mailSender.getJavaMailProperties().put("mail.smtp.ssl.socketFactory", "javax.net.ssl.SSLSocketFactory");
        return mailSender;
    }
}

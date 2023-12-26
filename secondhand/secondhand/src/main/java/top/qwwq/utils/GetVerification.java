package top.qwwq.utils;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GetVerification {
    public String getVerification(){
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder verificationCode = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(chars.length());
            verificationCode.append(chars.charAt(index));
        }
        return verificationCode.toString();
    }
}

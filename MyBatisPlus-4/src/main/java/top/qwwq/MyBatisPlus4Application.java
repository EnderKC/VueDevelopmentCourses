package top.qwwq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("top.qwwq.mapper")
public class MyBatisPlus4Application {

    public static void main(String[] args) {
        SpringApplication.run(MyBatisPlus4Application.class, args);
    }

}

package com.example.score.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * <br>
 * 用户前后端交互实体类
 * @author lihuanzhe
 * @version 1.0
 */
@Data
@NoArgsConstructor
public class UserInfoDto {

    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 用户角色
     */
    private String role;
    /**
     * 学号
     */
    private String stuid;

    /**
     * 姓名
     */
    private String stuname;

    /**
     * 性别
     */
    private String gender;

    /**
     * 出生日期
     */
    private LocalDate birthday;

    /**
     * 班级
     */
    private String classname;

    /**
     * 籍贯
     */
    private String address;
}

package com.example.score.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户类型字段分为教师和学生两种角色
 * </p>
 *
 * @author lihuanzhe
 * @since 2023-12-05
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    @TableId(value = "username", type = IdType.INPUT)
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

    /**
     * 用户角色
     */
    @TableField("role")
    private String role;

    /**
     * 学号
     */
    @TableField("stuid")
    private String stuid;

    /**
     * 学生信息
     */
    @TableField(exist = false)
    private Stuinfo stuInfo;
}

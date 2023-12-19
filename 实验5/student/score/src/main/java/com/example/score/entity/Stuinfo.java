package com.example.score.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 学生信息表
 * </p>
 *
 * @author lihuanzhe
 * @since 2023-12-05
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("stuinfo")
public class Stuinfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 学号
     */
    @TableId(value = "stuid", type = IdType.INPUT)
    private String stuid;

    /**
     * 姓名
     */
    @TableField("stuname")
    private String stuname;

    /**
     * 性别
     */
    @TableField("gender")
    private String gender;

    /**
     * 出生日期
     */
    @TableField("birthday")
    private LocalDate birthday;

    /**
     * 班级
     */
    @TableField("classname")
    private String classname;

    /**
     * 籍贯
     */
    @TableField("address")
    private String address;

    /**
     * 查询开始日期
     */
    @TableField(exist = false)
    private LocalDate beginTime;

    /**
     * 查询结束日期
     */
    @TableField(exist = false)
    private LocalDate endTime;

}

package com.example.score.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 成绩表
 * </p>
 *
 * @author lihuanzhe
 * @since 2023-12-05
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("score")
public class Score implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 编号,主键的数据类型必须是 String，数据库必须为字符串类型，自动生成 UUID进行赋值
     */
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 课程编号
     */
    @TableField("cid")
    private String cid;

    /**
     * 学号
     */
    @TableField("stuid")
    private String stuid;

    /**
     * 成绩
     */
    @TableField("score")
    private BigDecimal score;

    /**
     * 考试日期
     */
    @TableField("sdate")
    private LocalDate sdate;

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

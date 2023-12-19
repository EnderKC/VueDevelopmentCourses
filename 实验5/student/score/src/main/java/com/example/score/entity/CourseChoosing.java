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
 * 学生选课
 * </p>
 *
 * @author lihuanzhe
 * @since 2023-12-05
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("course_choosing")
public class CourseChoosing implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 学号
     */
    @TableField("stuid")
    private String stuid;

    /**
     * 课程编号
     */
    @TableField("cid")
    private String cid;

    /**
     * 选课日期
     */
    @TableField("cdate")
    private LocalDate cdate;
}

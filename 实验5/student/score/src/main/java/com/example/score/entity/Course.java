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
 * 课程表
 * </p>
 *
 * @author lihuanzhe
 * @since 2023-12-05
 */
@Data
@NoArgsConstructor
@Accessors(chain = true)
@TableName("course")
public class Course implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 课程编号
     */
    @TableId(value = "cid", type = IdType.INPUT)
    private String cid;

    /**
     * 课程名
     */
    @TableField("cname")
    private String cname;
}

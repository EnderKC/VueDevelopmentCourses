package com.example.score.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <br>
 * 复合主键类
 * @author lihuanzhe
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompKey {
    private String stuid;
    private String cid;
}

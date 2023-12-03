package top.qwwq.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
/*
 * 商品分类实体类
 */
public class Category {
    private int cateID;
    private String cateName;
    private List<Goods> goodsList;  // 添加 List<Goods> 属性，表示该类别下的所有货物
}

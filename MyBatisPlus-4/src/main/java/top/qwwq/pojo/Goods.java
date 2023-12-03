package top.qwwq.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
/*
 * 商品实体类
 */
public class Goods {
    @TableId
    private int goodsId;
    private int cateId;
    private int supplierId;
    private String goodsName;
    private float price;
}

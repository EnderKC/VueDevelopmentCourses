package top.qwwq.pojo;

import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-26
 */
@Getter
@Setter
@TableName("`order`")
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("order_id")
    private String orderId;

    private String sellerUserId;

    private String buyerUserId;

    private String goodsId;

    private LocalDate creatDate;

    private Integer statusId;
}

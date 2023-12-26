package top.qwwq.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.time.LocalDate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "Order对象", description = "订单表")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单号")
    @TableId("order_id")
    private String orderId;

    @ApiModelProperty("卖家用户id")
    private String sellerUserId;

    @ApiModelProperty("卖家的用户id")
    private String buyerUserId;

    @ApiModelProperty("商品id")
    private String goodsId;

    @ApiModelProperty("订单创建的时间")
    private LocalDate creatDate;

    @ApiModelProperty("订单的完成情况")
    private Integer statusId;
}

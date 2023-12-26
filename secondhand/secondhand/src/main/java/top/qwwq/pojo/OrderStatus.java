package top.qwwq.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 订单状态
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-26
 */
@Getter
@Setter
@TableName("order_status")
@ApiModel(value = "OrderStatus对象", description = "订单状态")
public class OrderStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("状态id")
    @TableId("status_id")
    private Integer statusId;

    @ApiModelProperty("状态名称")
    private Integer statusName;
}

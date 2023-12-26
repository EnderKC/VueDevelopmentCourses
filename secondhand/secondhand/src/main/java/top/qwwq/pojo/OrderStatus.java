package top.qwwq.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
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
public class OrderStatus implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId("status_id")
    private Integer statusId;

    private Integer statusName;
}

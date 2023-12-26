package top.qwwq.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.qwwq.pojo.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-26
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {

}

package top.qwwq.service.impl;

import top.qwwq.pojo.OrderStatus;
import top.qwwq.mapper.OrderStatusMapper;
import top.qwwq.service.IOrderStatusService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单状态 服务实现类
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-26
 */
@Service
public class OrderStatusServiceImpl extends ServiceImpl<OrderStatusMapper, OrderStatus> implements IOrderStatusService {

}

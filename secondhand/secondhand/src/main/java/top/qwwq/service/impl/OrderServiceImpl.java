package top.qwwq.service.impl;

import top.qwwq.pojo.Order;
import top.qwwq.mapper.OrderMapper;
import top.qwwq.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-26
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}

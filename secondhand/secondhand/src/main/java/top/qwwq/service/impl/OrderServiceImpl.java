package top.qwwq.service.impl;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import lombok.extern.slf4j.Slf4j;
import top.qwwq.mapper.GoodsMapper;
import top.qwwq.mapper.UserMapper;
import top.qwwq.pojo.Goods;
import top.qwwq.pojo.Order;
import top.qwwq.mapper.OrderMapper;
import top.qwwq.pojo.User;
import top.qwwq.service.EmailService;
import top.qwwq.service.IOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.qwwq.utils.GetUUID;
import top.qwwq.utils.ResponseVo;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.time.LocalDate;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-26
 */
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {
    OrderMapper orderMapper;
    GoodsMapper goodsMapper;
    EmailService emailService;
    UserMapper userMapper;

    public OrderServiceImpl(OrderMapper orderMapper, GoodsMapper goodsMapper, EmailService emailService, UserMapper userMapper) {
        this.orderMapper = orderMapper;
        this.goodsMapper = goodsMapper;
        this.emailService = emailService;
        this.userMapper = userMapper;
    }

    @Override
    public ResponseVo<JSONObject> placeOrder(User user, String goodsID) {
        // 检查商品库存是否足够
        Goods goods = goodsMapper.selectById(goodsID);
        log.info("goods:{}", goods);
        if (goods.getGoodsStock() <= 0) {
            return new ResponseVo(-1, "商品库存不足", null);
        }
        // 检查是否已经下单了该商品
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("buyer_user_id", user.getUserId())
                .eq("goods_id", goodsID);
        Order order = orderMapper.selectOne(orderQueryWrapper);
        if (order != null) {
            return new ResponseVo(-1, "已经下单了该商品", null);
        }
        // 下单商品
        order = new Order();
        order.setOrderId(GetUUID.getUUID());
        order.setBuyerUserId(user.getUserId());
        order.setSellerUserId(goods.getUserId());
        order.setGoodsId(goodsID);
        // 使用当前时间创建 LocalDateTime 对象
        LocalDateTime now = LocalDateTime.now();
        // 将 LocalDateTime 对象转换为 LocalDate 对象
        LocalDate currentDate = now.toLocalDate();
        order.setCreatDate(currentDate);
        order.setStatusId(0);
        orderMapper.insert(order);
        // 给卖家发送下单邮件
        // 卖家
        User seller = userMapper.selectById(goods.getUserId());
        emailService.sendOrderMessage(seller.getUserEmail(), goods.getGoodsName());
        return new ResponseVo(0, "下单成功", null);
    }

    @Override
    public ResponseVo<JSONObject> sendGoods(User user, String orderID) {
        emailService.sendOrderMessage(user.getUserEmail(), orderID);
        return updateOrderStatus(user, orderID, 2);
    }

    @Override
    public ResponseVo<JSONObject> finishOrder(User user, String orderID) {
        emailService.sendOrderMessage(user.getUserEmail(), orderID);
        return updateOrderStatus(user, orderID, 3);
    }

    public ResponseVo<JSONObject> updateOrderStatus(User user, String orderID, int status) {
        // 检查用户是否下单该商品
        Order order = orderMapper.selectById(orderID);
        if (order == null) {
            return new ResponseVo(-1, "订单不存在", null);
        }
        if (!order.getBuyerUserId().equals(user.getUserId()) || !order.getSellerUserId().equals(user.getUserId())) {
            return new ResponseVo(-1, "非法操作", null);
        }
        UpdateWrapper<Order> orderUpdateWrapper = new UpdateWrapper<Order>();
        orderUpdateWrapper.set("status_id", status);
        orderUpdateWrapper.eq("order_id", orderID);
        orderMapper.update(order, orderUpdateWrapper);
        return new ResponseVo(0, "更新成功", null);
    }

    @Override
    public ResponseVo<JSONArray> getMyOrder(String userID) {
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        orderQueryWrapper.eq("buyer_user_id", userID)
                .or()
                .eq("seller_user_id", userID);
        JSONArray jsonArray = new JSONArray();
        orderMapper.selectList(orderQueryWrapper).forEach(order -> {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("orderID", order.getOrderId());
            jsonObject.put("goodsID", order.getGoodsId());
            jsonObject.put("buyerUserID", order.getBuyerUserId());
            jsonObject.put("sellerUserID", order.getSellerUserId());
            jsonObject.put("statusID", order.getStatusId());
            jsonObject.put("creatDate", order.getCreatDate());
            jsonArray.add(jsonObject);
        });
        return new ResponseVo(0, "查询成功", jsonArray);
    }
}

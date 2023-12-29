package top.qwwq.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import top.qwwq.pojo.Goods;
import top.qwwq.pojo.Order;
import com.baomidou.mybatisplus.extension.service.IService;
import top.qwwq.pojo.User;
import top.qwwq.utils.ResponseVo;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-26
 */
public interface IOrderService extends IService<Order> {
    // 用户下单
    ResponseVo<JSONObject> placeOrder(User user, String goodsID);

    // 修改订单状态为已发货
    ResponseVo<JSONObject> sendGoods(User user, String orderID);

    // 修改订单状态为已完成
    ResponseVo<JSONObject> finishOrder(User user, String orderID);

    // 查询自己的订单
    ResponseVo<JSONArray> getMyOrder(String userID);


}

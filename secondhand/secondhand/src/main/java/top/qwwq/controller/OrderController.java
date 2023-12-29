package top.qwwq.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import top.qwwq.pojo.User;
import top.qwwq.service.IOrderService;
import top.qwwq.service.SystemService;
import top.qwwq.utils.ResponseVo;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-26
 */
@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {
    IOrderService iOrderService;
    SystemService service;
    public OrderController(IOrderService iOrderService, SystemService service) {
        this.iOrderService = iOrderService;
        this.service = service;
    }

    // 用户下单
    @PostMapping("/placeOrder")
    public ResponseVo<JSONObject> placeOrder(@RequestBody JSONObject jsonObject, ServletRequest request, ServletResponse response) {
        String goodsID = jsonObject.getString("goodsID");
        log.info("orderID:{}", goodsID);
        User user = service.getUserFromToken(request, response);
        return iOrderService.placeOrder(user, goodsID);
    }

    //修改订单状态为已发货
    @PostMapping("/sendGoods")
    public ResponseVo<JSONObject> sendGoods(@RequestBody JSONObject jsonObject, ServletRequest request, ServletResponse response) {
        String orderID = jsonObject.getString("orderID");
        User user = service.getUserFromToken(request, response);
        return iOrderService.sendGoods(user, orderID);
    }

    //修改订单状态为已完成
    @PostMapping("/finishOrder")
    public ResponseVo<JSONObject> finishOrder(@RequestBody JSONObject jsonObject, ServletRequest request, ServletResponse response) {
        String orderID = jsonObject.getString("orderID");
        User user = service.getUserFromToken(request, response);
        return iOrderService.finishOrder(user, orderID);
    }

    // 查询自己订单
    @PostMapping("/getMyOrder")
    public ResponseVo<JSONArray> getMyOrder(ServletRequest request, ServletResponse response) {
        User user = service.getUserFromToken(request, response);
        return iOrderService.getMyOrder(user.getUserId());
    }

}

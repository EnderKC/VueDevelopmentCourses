package top.qwwq.controller;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import top.qwwq.pojo.Goods;
import top.qwwq.pojo.PageRequest;
import top.qwwq.pojo.PageResult;
import top.qwwq.pojo.User;
import top.qwwq.service.IGoodsService;
import top.qwwq.service.SystemService;
import top.qwwq.utils.JwtUtil;
import top.qwwq.utils.ResponseVo;

/**
 * <p>
 * 商品 前端控制器
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-26
 */
@Slf4j
@RestController
@RequestMapping("/goods")
public class GoodsController {
    IGoodsService iGoodsService;
    SystemService service;

    public GoodsController(IGoodsService iGoodsService, SystemService service) {
        this.iGoodsService = iGoodsService;
        this.service = service;
    }

    // 用户发布商品
    @RequestMapping("/publishGoods")
    public ResponseVo<JSONObject> publishGoods(@RequestBody Goods goods, ServletRequest request, ServletResponse response) {
        User user = service.getUserFromToken(request, response);
        return iGoodsService.publishGoods(user, goods);
    }

    // 用户更新商品
    @RequestMapping("/updateGoods")
    public ResponseVo<JSONObject> updateGoods(@RequestBody Goods goods, ServletRequest request, ServletResponse response) {
        User user = service.getUserFromToken(request, response);
        return iGoodsService.updateGoods(user, goods);
    }

    // 用户删除商品
    @RequestMapping("/deleteGoods")
    public ResponseVo<JSONObject> deleteGoods(@RequestBody Goods goods, ServletRequest request, ServletResponse response) {
        User user = service.getUserFromToken(request, response);
        return iGoodsService.deleteGoods(user, goods);
    }

    // 用户查找商品
    @RequestMapping("/searchGoods")
    public ResponseVo<JSONArray> searchGoods(ServletRequest request, ServletResponse response) {
        User user = service.getUserFromToken(request, response);
        return iGoodsService.searchGoods(user);
    }

    // 根据条件查询所有商品
    @RequestMapping("/listGoods")
    public ResponseVo<PageResult> listGoods(@RequestBody JSONObject request) {
        log.info("request: {}", request);
        Goods goods = new Goods();
        goods.setGoodsName(request.getString("goodsName"));
        goods.setGoodsStock(request.getInteger("goodsStock"));
        if (request.containsKey("goodsDescription")) {
            goods.setGoodsDescription(request.getString("goodsDescription"));
        } else {
            goods.setGoodsDescription("");
        }
        PageRequest pageRequest = new PageRequest();
        // 判断 request中有没有 pageNum 和 pageSize 键
        if (!request.containsKey("pageNum") || !request.containsKey("pageSize")) {
            pageRequest.setPageNum(1);
            pageRequest.setPageSize(200);
        } else {
            pageRequest.setPageNum(request.getInteger("pageNum"));
            pageRequest.setPageSize(request.getInteger("pageSize"));
        }
        return iGoodsService.listByCriteria(goods, pageRequest);
    }
}

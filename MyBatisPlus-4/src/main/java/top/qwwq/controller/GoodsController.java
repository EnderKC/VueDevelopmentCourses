package top.qwwq.controller;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import top.qwwq.mapper.GoodsMapper;
import top.qwwq.pojo.Goods;
import top.qwwq.pojo.User;
import top.qwwq.service.IGoodsService;
import top.qwwq.utils.ResponseVo;

import java.util.Map;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-03
 */
@RestController
@RequestMapping("/goods")
@Slf4j
public class GoodsController {
    IGoodsService goodsService;
    GoodsMapper goodsMapper;
    public GoodsController(IGoodsService goodsService, GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
        this.goodsService = goodsService;
    }

    // 增加产品
    @RequestMapping("/addGoods")
    public ResponseVo<String> addGoods(@RequestBody JSONObject request){
        int goodsID = request.getIntValue("goodsID");
        String goodsName = request.getString("goodsName");
        float price = request.getFloat("price");
        int cateID = request.getIntValue("cateID");
        int supplierID = request.getIntValue("supplierID");
        Goods goods = new Goods();
        goods.setGoodsName(goodsName);
        goods.setGoodsId(goodsID);
        goods.setCateId(cateID);
        goods.setSupplierId(supplierID);
        goods.setPrice(price);
        log.info(goods.toString());
        // 添加商品
        try{
            goodsMapper.insert(goods);
            return new ResponseVo<>(0,"success");
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseVo<>(-1,"添加失败");
        }
    }
    // 删除
    @RequestMapping("/deleteGoods")
    public ResponseVo<String> deleteGoods(@RequestBody JSONObject request){
        int goodsID = request.getIntValue("goodsID");
        Goods goods = new Goods();
        goods.setGoodsId(goodsID);
        try{
            goodsMapper.deleteById(goodsID);
            return new ResponseVo<>(0,"success");
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseVo<>(-1,"删除失败");
        }
    }
    // 修改
    @RequestMapping("/updateGoods")
    public ResponseVo<String> updateGoods(@RequestBody JSONObject request){
        int goodsID = request.getIntValue("goodsID");
        String goodsName = request.getString("goodsName");
        float price = request.getFloat("price");
        int cateID = request.getIntValue("cateID");
        int supplierID = request.getIntValue("supplierID");
        Goods goods = new Goods();
        goods.setGoodsName(goodsName);
        goods.setGoodsId(goodsID);
        goods.setCateId(cateID);
        goods.setSupplierId(supplierID);
        goods.setPrice(price);
        try{
            goodsMapper.updateById(goods);
            return new ResponseVo<>(0,"success");
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseVo<>(-1,"修改失败");
        }
    }


    @RequestMapping("/selectGoods")
    public ResponseVo<Map<String,String>> selectUser(@RequestBody JSONObject request){
        Long goodsID = request.getLong("goodsID");
        return goodsService.selectGoods(goodsID);
    }
}

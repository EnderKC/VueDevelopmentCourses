package top.qwwq.controller;

import com.alibaba.fastjson2.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import top.qwwq.mapper.CategoryMapper;
import top.qwwq.pojo.Category;
import top.qwwq.utils.ResponseVo;

import java.util.List;
import java.util.Map;


/**
 * <p>
 * 商品类别表 前端控制器
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-03
 */
@RestController
@RequestMapping("/category")
public class CategoryController {
    CategoryMapper categoryMapper;
    public CategoryController(CategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @RequestMapping("/selectCategoryAndGoods")
    public ResponseVo<Category> selectCategoryAndGoods(@RequestBody JSONObject requestJson) {
        int cateID = requestJson.getInteger("cateID");
        Category stringStringMap = categoryMapper.selectCategoryAndGoods(cateID);
        return new ResponseVo<>(0, "success", stringStringMap);
    }

    @RequestMapping("/selectCategoryAndGoodsByJoin")
    public ResponseVo<List<Map<String,String>>> selectCategoryAndGoodsByJoin(@RequestBody JSONObject requestJson) {
        int cateID = requestJson.getInteger("cateID");
        List<Map<String,String>> list = categoryMapper.selectCategoryAndGoodsByJoin(cateID);
        return new ResponseVo<>(0, "success", list);
    }

}

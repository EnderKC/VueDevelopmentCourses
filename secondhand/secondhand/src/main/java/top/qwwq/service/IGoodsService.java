package top.qwwq.service;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.ResponseBody;
import top.qwwq.pojo.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import top.qwwq.pojo.PageRequest;
import top.qwwq.pojo.PageResult;
import top.qwwq.pojo.User;
import top.qwwq.utils.ResponseVo;

import java.util.List;

/**
 * <p>
 * 商品 服务类
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-26
 */
public interface IGoodsService extends IService<Goods> {
    // 用户发布商品
    ResponseVo<JSONObject> publishGoods(User user, Goods goods);

    // 用户更新商品
    ResponseVo<JSONObject> updateGoods(User user, Goods goods);

    // 用户删除商品
    ResponseVo<JSONObject> deleteGoods(User user, Goods goods);

    // 用户查找商品
    ResponseVo<JSONArray> searchGoods(User user);

    // 按照条件，查找所有商品
    ResponseVo<PageResult> listByCriteria(Goods criteria, PageRequest pageRequest);


}

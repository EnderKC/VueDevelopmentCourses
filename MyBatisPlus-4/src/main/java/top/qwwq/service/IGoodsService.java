package top.qwwq.service;

import top.qwwq.pojo.Goods;
import com.baomidou.mybatisplus.extension.service.IService;
import top.qwwq.utils.ResponseVo;

import java.util.Map;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-03
 */
public interface IGoodsService extends IService<Goods> {
    ResponseVo<Map<String,String>> selectGoods(long goodsID);
}

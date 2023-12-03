package top.qwwq.mapper;

import top.qwwq.pojo.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Map;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-03
 */
public interface GoodsMapper extends BaseMapper<Goods> {
    // 查
    Map<String,String> selectGoods(long goodsID);
}

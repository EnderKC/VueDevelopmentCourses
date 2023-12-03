package top.qwwq.service.impl;

import top.qwwq.pojo.Goods;
import top.qwwq.mapper.GoodsMapper;
import top.qwwq.service.IGoodsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import top.qwwq.utils.ResponseVo;

import java.util.Map;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-03
 */
@Service
public class GoodsServiceImpl extends ServiceImpl<GoodsMapper, Goods> implements IGoodsService {
    GoodsMapper goodsMapper;
    public GoodsServiceImpl(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    @Override
    public ResponseVo<Map<String, String>> selectGoods(long goodsID) {
        Map<String,String> goods = goodsMapper.selectGoods(goodsID);
        if(goods == null){
            return new ResponseVo(-1,"商品不存在");
        }
        return new ResponseVo<>(0,"success",goods);
    }
}

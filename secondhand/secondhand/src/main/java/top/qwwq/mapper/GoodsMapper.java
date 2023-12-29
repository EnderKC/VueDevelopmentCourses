package top.qwwq.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.qwwq.pojo.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 商品 Mapper 接口
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-26
 */
@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {

    // 按照条件查找商品
    List<Goods> listByCriteria(Goods criteria);

}

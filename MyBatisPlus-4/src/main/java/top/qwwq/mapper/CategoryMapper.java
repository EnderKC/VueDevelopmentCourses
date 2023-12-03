package top.qwwq.mapper;

import top.qwwq.pojo.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品类别表 Mapper 接口
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-03
 */
public interface CategoryMapper extends BaseMapper<Category> {

    // 1+n 方式查询
    Category selectCategoryAndGoods(int cateID);

    // 多表连接方式查询
    List<Map<String,String>> selectCategoryAndGoodsByJoin(int cateID);

}

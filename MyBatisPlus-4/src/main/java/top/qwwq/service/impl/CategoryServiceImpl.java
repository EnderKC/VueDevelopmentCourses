package top.qwwq.service.impl;

import top.qwwq.pojo.Category;
import top.qwwq.mapper.CategoryMapper;
import top.qwwq.service.ICategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品类别表 服务实现类
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-03
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements ICategoryService {

}

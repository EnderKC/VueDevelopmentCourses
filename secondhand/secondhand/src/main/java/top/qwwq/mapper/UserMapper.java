package top.qwwq.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.qwwq.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-26
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}

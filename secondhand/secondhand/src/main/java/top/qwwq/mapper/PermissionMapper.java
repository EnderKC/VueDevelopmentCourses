package top.qwwq.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.qwwq.pojo.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 权限 Mapper 接口
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-26
 */
@Mapper
public interface PermissionMapper extends BaseMapper<Permission> {

}

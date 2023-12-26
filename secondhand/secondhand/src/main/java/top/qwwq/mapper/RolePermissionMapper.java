package top.qwwq.mapper;

import org.apache.ibatis.annotations.Mapper;
import top.qwwq.pojo.RolePermission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 角色和权限的对应关系 Mapper 接口
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-26
 */
@Mapper
public interface RolePermissionMapper extends BaseMapper<RolePermission> {

}

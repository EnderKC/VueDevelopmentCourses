package top.qwwq.service.impl;

import top.qwwq.pojo.RolePermission;
import top.qwwq.mapper.RolePermissionMapper;
import top.qwwq.service.IRolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色和权限的对应关系 服务实现类
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-26
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements IRolePermissionService {

}

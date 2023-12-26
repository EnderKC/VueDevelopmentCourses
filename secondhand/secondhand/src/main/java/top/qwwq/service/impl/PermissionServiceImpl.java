package top.qwwq.service.impl;

import top.qwwq.pojo.Permission;
import top.qwwq.mapper.PermissionMapper;
import top.qwwq.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-26
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}

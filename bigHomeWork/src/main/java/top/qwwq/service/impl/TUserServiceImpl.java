package top.qwwq.service.impl;

import top.qwwq.pojo.TUser;
import top.qwwq.mapper.TUserMapper;
import top.qwwq.service.ITUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author EnderKC
 * @since 2023-12-21
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements ITUserService {

}

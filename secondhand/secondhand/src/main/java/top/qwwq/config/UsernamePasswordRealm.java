package top.qwwq.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;
import top.qwwq.mapper.RoleMapper;
import top.qwwq.mapper.RolePermissionMapper;
import top.qwwq.mapper.UserMapper;
import top.qwwq.pojo.Role;
import top.qwwq.pojo.RolePermission;
import top.qwwq.pojo.User;
import top.qwwq.utils.JwtUtil;
import top.qwwq.utils.RedisUtil;
import top.qwwq.mapper.PermissionMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component("UsernamePasswordRealm")
public class UsernamePasswordRealm extends AuthorizingRealm {

    private final UserMapper userMapper;
    private final RedisUtil redisUtil;
    private final PermissionMapper permissionMapper;
    private final RoleMapper roleMapper;
    private final RolePermissionMapper rolePermissionMapper;
    public UsernamePasswordRealm(UserMapper userMapper, RedisUtil redisUtil, PermissionMapper permissionMapper, RoleMapper roleMapper, RolePermissionMapper rolePermissionMapper) {
        this.userMapper = userMapper;
        this.redisUtil = redisUtil;
        this.permissionMapper = permissionMapper;
        this.roleMapper = roleMapper;
        this.rolePermissionMapper = rolePermissionMapper;
    }

    /**
     * 必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 用来进行身份认证，也就是说验证用户输入的账号和密码是否正确，
     * 获取身份验证信息，错误抛出异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        if (null == token) {
            throw new AuthenticationException("token为空!");
        }
        // 解密获得username，用于和数据库进行对比
        String id = JwtUtil.parseTokenId(token);
        User user = userMapper.selectById(id);
        if (null == user) {
            throw new AuthenticationException("用户不存在!");
        }
        // 校验token是否过期
        if (!tokenRefresh(token, user)) {
            throw new AuthenticationException("Token已过期!");
        }
        return new SimpleAuthenticationInfo(user, token,"MyRealm");
    }

    /**
     * 获取用户权限信息，包括角色以及权限。
     * 只有当触发检测用户权限时才会调用此方法，例如checkRole,checkPermissionJwtToken
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        log.info("————权限认证 [ roles、permissions]————");
        User user = null;
        if (principals != null) {
            user = (User) principals.getPrimaryPrincipal();
        }
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if(user != null){
            // 用户拥有的角色
            Role role = roleMapper.selectById(user.getUserRole());
            simpleAuthorizationInfo.addRole(role.getRoleName());
            log.info("角色为:"+role.getRoleName());
            // 获取用户权限的集合
            QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
            wrapper.eq("role_id",user.getUserRole());
            List<RolePermission> rolePermissions = rolePermissionMapper.selectList(wrapper);
            Set<String> permissions = new HashSet<>();
            for (RolePermission rolePermission : rolePermissions) {
                permissions.add(permissionMapper.selectById(rolePermission.getPermissionId()).getPerms());
            }
            simpleAuthorizationInfo.addStringPermissions(permissions);
            log.info("权限有:"+permissions.toString());
        }
        return simpleAuthorizationInfo;
    }

    /**
     * JWT Token续签：
     * 业务逻辑：登录成功后，用户在未过期时间内继续操作，续签token。
     *         登录成功后，空闲超过过期时间，返回token已失效，重新登录。
     * 实现逻辑：
     *    1.登录成功后将token存储到redis里面(这时候k、v值一样都为token)，并设置过期时间为token过期时间
     *    2.当用户请求时token值还未过期，则重新设置redis里token的过期时间。
     *    3.当用户请求时token值已过期，但redis中还在，则JWT重新生成token并覆盖v值(这时候k、v值不一样了)，然后设置redis过期时间。
     *    4.当用户请求时token值已过期，并且redis中也不存在，则用户空闲超时，返回token已失效，重新登录。
     */
    public boolean tokenRefresh(String token, User user) {
        String cacheToken = String.valueOf(redisUtil.get(token));
        // 过期后会得到"null"值，所以需判断字符串"null"
        if (cacheToken != null && !cacheToken.isEmpty() && !"null".equals(cacheToken)) {
            // 校验token有效性
            if (!JwtUtil.isVerify(cacheToken)) {
                // 生成token
                String newToken = JwtUtil.createToken(user);
                // 将token存入redis,并设置超时时间
                redisUtil.set(token, newToken, JwtUtil.getExpireTime());
            } else {
                // 重新设置超时时间
                redisUtil.expire(token, JwtUtil.getExpireTime());
            }
            log.info("存入redis的过期时间："+redisUtil.getExpire(token));
            return true;
        }
        return false;
    }
}
package com.example.score.handler;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * 自定义身份认证token类
 * <br>
 *
 * @author lihuanzhe
 * @version 1.0
 */
public class CustomAuthenticationToken extends AbstractAuthenticationToken {

    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    /**
     * 认证主体，MyUser类型
     */
    private final Object principal;

    /**
     * 构造函数
     * @param principal  认证主体
     */
    public CustomAuthenticationToken(Object principal) {
        super(null);
        this.principal = principal;
        setAuthenticated(false);
    }

    /**
     * 构造函数
     * @param principal  认证主体
     * @param authorities 权限
     */
    public CustomAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true);
    }

    /**
     * 获取凭证
     * @return 返回凭证
     */
    @Override
    public Object getCredentials() {
        return null;
    }

    /**
     * 获取认证主体
     * @return 获取认证主体
     */
    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    /**
     * 设置是否已认证
     * @param isAuthenticated <code>true</code> if the token should be trusted (which may
     * result in an exception) or <code>false</code> if the token should not be trusted
     * @throws IllegalArgumentException 异常
     */
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        if (isAuthenticated) {
            throw new IllegalArgumentException(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }

        super.setAuthenticated(false);
    }

    /**
     * 消除凭证
     */
    @Override
    public void eraseCredentials() {
        super.eraseCredentials();
    }
}

package com.diyiliu.support.shiro.realm;

import com.diyiliu.support.shiro.JwtToken;
import com.diyiliu.support.util.JwtUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.util.StringUtils;

/**
 * Description: JwtRealm
 * Author: DIYILIU
 * Update: 2019-05-29 10:50
 */
public class JwtRealm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();

        // 解密获得username，用于和数据库进行对比
        String username = JwtUtil.getUsername(token);
        if (username == null) {
            throw new AuthenticationException("token无效");
        }

        if (StringUtils.isEmpty(username)) {
            throw new AccountException("用户名不能为空");
        }

        String password = "87166669";
        if (!JwtUtil.verify(token, username, password)) {
            throw new AuthenticationException("用户名或密码错误");
        }

        return new SimpleAuthenticationInfo(token, token, "jwtRealm");
    }
}

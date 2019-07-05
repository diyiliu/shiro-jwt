package com.diyiliu.support.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * Description: UserRealm
 * Author: DIYILIU
 * Update: 2019-06-12 14:55
 */
public class UserRealm extends AuthorizingRealm {

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        if (username == null) {
            // 找不到用户
            throw new UnknownAccountException();
        }

        /*if (Boolean.TRUE.equals(user.getLocked())) {
            // 用户锁定
            throw new LockedAccountException();
        }*/

        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                username,
                "dd2ff6824accbefd23edc18c234ad313",
                ByteSource.Util.bytes("6a75262bcb161d22eae1638f4a75bd14"),
                getName());

        return authenticationInfo;
    }
}

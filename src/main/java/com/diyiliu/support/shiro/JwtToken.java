package com.diyiliu.support.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * Description: JwtToken
 * Author: DIYILIU
 * Update: 2019-05-29 10:48
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}

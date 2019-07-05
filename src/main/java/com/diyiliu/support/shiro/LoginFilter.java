package com.diyiliu.support.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Description: LoginFilter
 * Author: DIYILIU
 * Update: 2019-07-05 09:38
 */
public class LoginFilter extends FormAuthenticationFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        if (isLoginAttempt(request)) {
            try {
                return executeLogin(request, response);
            } catch (Exception e) {
                responseError(response);
            }
        }
        return true;
    }


    protected boolean isLoginAttempt(ServletRequest request) {
        HttpServletRequest req = (HttpServletRequest) request;
        String authorization = req.getHeader(AUTHORIZATION_HEADER);
        return StringUtils.isEmpty(authorization);
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        responseError(response);
        return true;
    }

    /**
     * 将非法请求跳转到 /unauthorized
     */
    private void responseError(ServletResponse response) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.sendRedirect("/unauthorized");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

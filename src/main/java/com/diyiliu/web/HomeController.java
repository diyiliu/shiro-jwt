package com.diyiliu.web;

import com.diyiliu.support.util.JwtUtil;
import com.diyiliu.support.util.ResponseUtil;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: HomeController
 * Author: DIYILIU
 * Update: 2019-05-29 10:46
 */

@Api(description = "首页")
@RestController
public class HomeController {

    @GetMapping("/")
    public Object index() {

        return ResponseUtil.ok();
    }

    @PostMapping("/login")
    public Object login(String username, String password) {

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        String msg = "登录失败";
        try {
            subject.login(token);
            String secret = "87166669";
            return ResponseUtil.ok("登录成功", JwtUtil.sign(username, secret));
        } catch (IncorrectCredentialsException e) {
            msg = "密码错误";
        } catch (LockedAccountException e) {
            msg = "登录失败，该用户已被冻结";
        } catch (AuthenticationException e) {
            msg = "该用户不存在";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseUtil.fail(401, msg);
    }

    @GetMapping("/login")
    public Object toLogin() {

        return ResponseUtil.unAuth();
    }


    @RequiresAuthentication
    @GetMapping("/home")
    public Object home() {

        return ResponseUtil.ok("欢迎");
    }

    @GetMapping("/unauthorized")
    public Object unauthorized() {

        return ResponseUtil.unAuth();
    }
}

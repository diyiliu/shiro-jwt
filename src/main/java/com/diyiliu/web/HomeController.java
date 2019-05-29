package com.diyiliu.web;

import com.diyiliu.support.util.JwtUtil;
import com.diyiliu.support.util.PasswordHelper;
import com.diyiliu.support.util.ResponseUtil;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Description: HomeController
 * Author: DIYILIU
 * Update: 2019-05-29 10:46
 */

@Api(description = "首页")
@RestController
public class HomeController {

    @Resource
    private PasswordHelper passwordHelper;

    @GetMapping("/")
    public Object index() {

        return ResponseUtil.ok();
    }

    @PostMapping("/login")
    public Object login(String username, String password) {
        String salt = "6a75262bcb161d22eae1638f4a75bd14";
        String enPwd = passwordHelper.encryptPassword(username, password, salt);

        return ResponseUtil.ok("登录成功", JwtUtil.sign(username, enPwd));
    }

    @GetMapping("/login")
    public Object toLogin() {

        return ResponseUtil.unLogin();
    }

    @GetMapping("/home")
    public Object home() {

        return ResponseUtil.ok("欢迎");
    }

    @GetMapping("/unauthorized")
    public Object unAuth() {

        return ResponseUtil.unAuth();
    }
}

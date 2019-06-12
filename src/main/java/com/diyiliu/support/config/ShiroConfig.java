package com.diyiliu.support.config;

import com.diyiliu.support.shiro.JwtFilter;
import com.diyiliu.support.shiro.realm.JwtRealm;
import com.diyiliu.support.shiro.realm.UserRealm;
import com.diyiliu.support.util.PasswordHelper;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Description: ShiroConfig
 * Author: DIYILIU
 * Update: 2019-05-23 10:32
 */

@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilter() {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager());
        factoryBean.setLoginUrl("/login");
        factoryBean.setSuccessUrl("/home");
        factoryBean.setUnauthorizedUrl("/unauthorized");

        Map<String, Filter> filters = new LinkedHashMap<>();
        filters.put("authc", new JwtFilter());
        factoryBean.setFilters(filters);

        Map<String, String> filterChainDefinitionMap = new HashMap();
        // swagger ui
        filterChainDefinitionMap.put("/swagger-ui.html", "anon");
        filterChainDefinitionMap.put("/swagger-resources", "anon");
        filterChainDefinitionMap.put("/v2/api-docs", "anon");
        filterChainDefinitionMap.put("/webjars/springfox-swagger-ui/**", "anon");

        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/unauthorized", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        factoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);

        return factoryBean;
    }

    @Bean
    public JwtRealm jwtRealm() {

        return new JwtRealm();
    }


    @Bean
    public UserRealm userRealm() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashIterations(2);
        matcher.setHashAlgorithmName("MD5");

        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(matcher);

        return userRealm;
    }


    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealms(Arrays.asList(userRealm(), jwtRealm()));

        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }

    @Bean
    public PasswordHelper passwordHelper() {
        PasswordHelper helper = new PasswordHelper();
        helper.setHashAlgorithmName("MD5");
        helper.setHashIterations(2);

        return helper;
    }
}

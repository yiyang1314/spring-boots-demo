package com.boot.shiro.config;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.boot.shiro.service.UserService;

public class Realm extends AuthorizingRealm {

    @Autowired
    private UserService userService;
    
    //授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //从凭证中获得用户名
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        //根据用户名查询用户对象
        User user = userService.getUserByUserName(username);
        //查询用户拥有的角色
        List<Role> list = roleService.findByUserId(user.getId());
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        for (Role role : list) {
            //赋予用户角色
            info.addStringPermission(role.getRole());
        }
        return info;
    }

    //认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //获得当前用户的用户名
        String username = (String) authenticationToken.getPrincipal();

        //从数据库中根据用户名查找用户
        User user = userService.getUserByUserName(username);
        if (userService.getUserByUserName(username) == null) {
            throw new UnknownAccountException(
                    "没有在本系统中找到对应的用户信息。");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(),getName());
        return info;
    }

}
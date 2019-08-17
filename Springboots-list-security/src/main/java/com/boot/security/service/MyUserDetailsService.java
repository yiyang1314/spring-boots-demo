package com.boot.security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.boot.security.mapper.UserMapper;
import com.boot.security.pojo.SysPermission;
import com.boot.security.pojo.SysRole;
import com.boot.security.pojo.SysUser;

@Service
public class MyUserDetailsService implements UserDetailsService {

    	@Autowired
    private UserMapper userMapper;

    /**
     * 授权的时候是对角色授权，认证的时候应该基于资源，而不是角色，因为资源是不变的，而用户的角色是会变的
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = userMapper.findByUserName(username);
        if (null == user) {
            throw new UsernameNotFoundException(username);
        }
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        List<SysRole> roleList = user.getRoleList();
        if(roleList == null || roleList.size() == 0){
            return new User(user.getUserName(), user.getPassword(), authorities);
        }
        for (SysRole role : roleList) {
            List<SysPermission> permList = role.getPermissionList();
            if(permList == null){
                continue;
            }
            for(SysPermission permission : permList){
                //添加用户的权限
                authorities.add(new SimpleGrantedAuthority(permission.getCode()));
            }
        }
        return new User(user.getUserName(), user.getPassword(), authorities);
    }
}

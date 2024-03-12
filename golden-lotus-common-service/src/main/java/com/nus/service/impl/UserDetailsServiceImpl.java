package com.nus.service.impl;

import com.nus.constant.LoginConstant;
import com.nus.constant.MessageConstant;
import com.nus.constant.PermissionConstant;
import com.nus.exception.LoginFailedException;
import com.nus.mapper.AdminMapper;
import com.nus.mapper.ChefMapper;
import com.nus.mapper.UserMapper;
import com.nus.pojo.entity.LoginUser;
import com.nus.pojo.entity.People;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private ChefMapper chefMapper;

    @Override
    public UserDetails loadUserByUsername(String fullUsername) throws UsernameNotFoundException {
        String[] parts = fullUsername.split(":");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid username format");
        }
        String type = parts[0];
        String username = parts[1];
        People people = null;
        List<String> permissions = new ArrayList<>();

        if (type.equals(LoginConstant.ADMIN_LOGIN)){
            people = adminMapper.getByUserName(username);
            permissions = PermissionConstant.ADMIN_PERMISSIONS;
        }

        if (type.equals(LoginConstant.USER_LOGIN)){
            people = userMapper.getByUserName(username);
            permissions = PermissionConstant.USER_PERMISSIONS;
        }

        if (type.equals(LoginConstant.CHEF_LOGIN)){
            people = chefMapper.getByUserName(username);
            permissions = PermissionConstant.CHEF_PERMISSIONS;
        }

        if(Objects.isNull(people)){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        return new LoginUser(people, type, permissions);
    }
}

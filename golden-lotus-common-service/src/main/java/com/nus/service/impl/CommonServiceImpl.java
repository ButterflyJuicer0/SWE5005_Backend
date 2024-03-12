package com.nus.service.impl;

import com.nus.constant.LoginConstant;
import com.nus.constant.MessageConstant;
import com.nus.context.BaseContext;
import com.nus.exception.AccountNotFoundException;
import com.nus.exception.PasswordErrorException;
import com.nus.mapper.AdminMapper;
import com.nus.mapper.ChefMapper;
import com.nus.mapper.UserMapper;
import com.nus.pojo.dto.AccountLoginDTO;
import com.nus.pojo.entity.LoginUser;
import com.nus.pojo.entity.People;
import com.nus.service.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Set;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * Login
     * Done by CHEN WEIJIAN
     *
     * @param accountLoginDTO
     * @return
     */
//    @Override
//    public People login(AccountLoginDTO accountLoginDTO) {
//        // Get username and password
//        String username = accountLoginDTO.getUsername();
//        String password = accountLoginDTO.getPassword();
//        String type = accountLoginDTO.getType();
//
//        People people = null;
//
//        if (type.equals(LoginConstant.ADMIN_LOGIN)){
//            people = adminMapper.getByUserName(username);
//        }
//
//        if (type.equals(LoginConstant.USER_LOGIN)){
//            people = userMapper.getByUserName(username);
//        }
//
//        if (type.equals(LoginConstant.CHEF_LOGIN)){
//            people = chefMapper.getByUserName(username);
//        }
//
//        //if null, this user is not in database
//        //if not null, the user is in database
//        if (people == null){
//            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
//        }
//
//        password = DigestUtils.md5DigestAsHex(password.getBytes());
//
//        //check password
//        if (!password.equals(people.getPassword())){
//            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
//        }
//
//        return people;
//    }

    @Override
    public People login(AccountLoginDTO accountLoginDTO) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(accountLoginDTO.getType() + ":" + accountLoginDTO.getUsername(), accountLoginDTO.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        if (authenticate == null){
            throw new RuntimeException("Login failed");
        }

        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();

        String key = generateRedisKey(loginUser);
        redisTemplate.opsForValue().set(key, loginUser);

        return loginUser.getPeople();
    }

    @Override
    public void logout() {
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.
                        getContext().
                        getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

        String key = generateRedisKey(loginUser);
        redisTemplate.delete(key);
    }

    private String generateRedisKey(LoginUser loginUser){
        Long id = loginUser.getPeople().getId();
        String type = loginUser.getType();
        String account = "";
        if (LoginConstant.ADMIN_LOGIN.equals(type)){
            account = "admin";
        }else if (LoginConstant.CHEF_LOGIN.equals(type)){
            account = "chef";
        }else if (LoginConstant.USER_LOGIN.equals(type)){
            account = "user";
        }else{
            throw new RuntimeException("Type Format Error");
        }

        return account + "Id_" + id;
    }
}

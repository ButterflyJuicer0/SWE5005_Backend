package com.nus.filter;

import com.nus.constant.LoginConstant;
import com.nus.constant.MessageConstant;
import com.nus.context.BaseContext;
import com.nus.exception.UserNotLoginException;
import com.nus.pojo.entity.LoginUser;
import com.nus.properties.JwtProperties;
import com.nus.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@Slf4j
public class JWTAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProperties jwtProperties;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)){
            filterChain.doFilter(request, response);
            return;
        }

        Long id;
        String type = "";
        try {
            Claims claims = JwtUtil.parseJWT(jwtProperties.getSecretKey(), token);
            id = Long.valueOf(claims.get("id").toString());
            type = (String) claims.get("type");

//            if (LoginConstant.ADMIN_LOGIN.equals(type) || LoginConstant.USER_LOGIN.equals(type) || LoginConstant.CHEF_LOGIN.equals(type)){
//                setCurrentUserIdWhenJWT(id);
//            }

            if (LoginConstant.USER_LOGIN.equals(type)){
                setCurrentUserIdWhenJWT(id);
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid JWT");
        }

//        String account = "";
//        if (LoginConstant.ADMIN_LOGIN.equals(type)){
//            account = "admin";
//        }else if (LoginConstant.CHEF_LOGIN.equals(type)){
//            account = "chef";
//        }else if (LoginConstant.USER_LOGIN.equals(type)){
//            account = "user";
//        }else{
//            throw new RuntimeException("Type Format Error");
//        }

        String account = "user";

        String key = account + "Id_" + id;
        LoginUser loginUser = (LoginUser)redisTemplate.opsForValue().get(key);
        if (Objects.isNull(loginUser)){
            throw new UserNotLoginException(MessageConstant.USER_NOT_LOGIN);
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }

    public void setCurrentUserIdWhenJWT(long id){
        log.info("Current Person ID：{}", id);
        BaseContext.setCurrentId(id);
    }
}

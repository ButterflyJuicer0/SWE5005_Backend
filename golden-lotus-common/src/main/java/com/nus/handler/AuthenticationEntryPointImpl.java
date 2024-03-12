package com.nus.handler;

import com.alibaba.fastjson.JSON;
import com.nus.constant.MessageConstant;
import com.nus.result.Result;
import com.nus.utils.WebUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        Result result = new Result(HttpStatus.UNAUTHORIZED.value(), MessageConstant.AUTHENTICATION_FAILED);
        String json = JSON.toJSONString(result);
        WebUtil.renderString(response, json);
    }
}

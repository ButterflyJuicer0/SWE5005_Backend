package com.nus.handler;

import com.alibaba.fastjson.JSON;
import com.nus.constant.MessageConstant;
import com.nus.result.Result;
import com.nus.utils.WebUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        Result result = new Result(HttpStatus.FORBIDDEN.value(), MessageConstant.ACCESS_DENIED);
        String json = JSON.toJSONString(result);
        WebUtil.renderString(response,json);
    }
}
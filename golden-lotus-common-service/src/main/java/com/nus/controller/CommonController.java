package com.nus.controller;

import com.nus.pojo.dto.AccountLoginDTO;
import com.nus.pojo.entity.People;
import com.nus.pojo.vo.AccountLoginVO;
import com.nus.properties.JwtProperties;
import com.nus.result.Result;
import com.nus.service.CommonService;
import com.nus.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Common Service
 * Done by CHEN WEIJIAN
 */
@RestController
@RequestMapping("/common")
@Api(tags = "Common Relevant Interface")
@Slf4j
@CrossOrigin
public class CommonController {

    @Autowired
    private CommonService commonService;

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * Login
     * Done by CHEN WEIJIAN
     * @param accountLoginDTO
     * @return
     */
//    @PostMapping("/login")
//    @ApiOperation(value = "login")
//    public Result<AccountLoginVO> login(@RequestBody AccountLoginDTO accountLoginDTO){
//        log.info("Login Start: {}",accountLoginDTO);
//        //login
//        People people = commonService.login(accountLoginDTO);
//
//        //if login successfully, generate JWT token
//        Map<String, Object> claims = new HashMap<>();
//
//        claims.put(accountLoginDTO.getType(), people.getId());
//        String token = JwtUtil.createJWT(
//                jwtProperties.getSecretKey(),
//                jwtProperties.getTtl(),
//                claims);
//
//        AccountLoginVO accountLoginVO = AccountLoginVO.builder()
//                .id(people.getId())
//                .name(people.getName())
//                .userName(people.getName())
//                .token(token)
//                .build();
//
//        return Result.success(accountLoginVO);
//    }

    @PostMapping("/login")
    @ApiOperation(value = "login")
    public Result<AccountLoginVO> login(@RequestBody AccountLoginDTO accountLoginDTO){
        log.info("Login Start: {}",accountLoginDTO);
        //login
        People people = commonService.login(accountLoginDTO);

        //if login successfully, generate JWT token
        Map<String, Object> claims = new HashMap<>();
        claims.put("type", accountLoginDTO.getType());
        claims.put("id", people.getId());

        String token = JwtUtil.createJWT(
                jwtProperties.getSecretKey(),
                jwtProperties.getTtl(),
                claims);

        AccountLoginVO accountLoginVO = AccountLoginVO.builder()
                .id(people.getId())
                .name(people.getName())
                .userName(people.getName())
                .token(token)
                .build();

        return Result.success(accountLoginVO);
    }

    /**
     * User Logout
     * Done by CHEN WEIJIAN
     */
    @PostMapping("/logout")
    @ApiOperation(value = "logout")
    @PreAuthorize("hasAuthority('logout')")
    public Result<String> logout(){
        log.info("User Logout...");
        commonService.logout();
        return Result.success();
    }
}

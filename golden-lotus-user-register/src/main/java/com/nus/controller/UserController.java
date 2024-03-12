package com.nus.controller;

import com.nus.constant.MessageConstant;
import com.nus.exception.AccountAlreadyExistsException;
import com.nus.pojo.dto.UserDTO;
import com.nus.result.Result;
import com.nus.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Slf4j
@CrossOrigin
@Api(tags = "User Relevant Interface")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * User Register
     * Done by CHEN WEIJIAN
     * @param userDTO
     * @return
     */
    @PostMapping("/register")
    @ApiOperation(value = "register")
    public Result register(@RequestBody UserDTO userDTO){
        log.info("User Register: {}", userDTO);
        try {
            userService.save(userDTO);
            return Result.success(MessageConstant.REGISTER_SUCCESS);
        } catch (AccountAlreadyExistsException e) {
            log.info("Warning: " + e.getMessage());
            return Result.error(e.getMessage());
        }
    }
}

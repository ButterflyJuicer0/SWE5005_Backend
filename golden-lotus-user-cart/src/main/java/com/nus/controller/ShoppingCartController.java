package com.nus.controller;

import com.nus.pojo.dto.ShoppingCartDTO;
import com.nus.pojo.entity.ShoppingCart;
import com.nus.result.Result;
import com.nus.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingcart")
@Slf4j
@CrossOrigin
@Api(tags = "ShoppingCart Relevant Interface")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PutMapping("/add")
    @ApiOperation(value = "add dish")
    @PreAuthorize("hasAuthority('add to cart')")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("User adds dish to shopping cart");
        shoppingCartService.addToShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    @ApiOperation(value = "remove dish")
    @PutMapping("/remove")
    @PreAuthorize("hasAuthority('remove from cart')")
    public Result remove(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("User remove dish from shopping cart");
        shoppingCartService.removeFromShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    @GetMapping("/show/dishes")
    @ApiOperation(value = "show all dishes")
    public Result<List<ShoppingCart>> show(){
        log.info("Show all dishes to user");
        List<ShoppingCart> list = shoppingCartService.showAll();
        return Result.success(list);
    }

    @DeleteMapping
    @ApiOperation(value = "clear shopping cart")
    @PreAuthorize("hasAuthority('clear cart')")
    public Result<String> clear(){
        log.info("User starts to check out");
        shoppingCartService.emptyShoppingCart();
        return Result.success();
    }

    @GetMapping("/total")
    @ApiOperation(value = "show total amount")
    public Result<Double> getTotal(){
        log.info("Show total amount of shopping cart");
        Double total = shoppingCartService.calculateTotal();
        return Result.success(total);
    }
}

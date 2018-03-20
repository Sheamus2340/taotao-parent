package com.boco.taotao.portal.controller;

import com.boco.taotao.portal.service.CartService;
import com.boco.taotao.vo.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Sheamus on 2018/3/19.
 */
@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @RequestMapping("/add/{itemId}")
    public String addCartItem(@PathVariable Long itemId,
                              @RequestParam(defaultValue = "1") Integer num,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        TaotaoResult result = cartService.addCartItem(request, response,itemId,num);
        return "cartSuccess";
    }

}

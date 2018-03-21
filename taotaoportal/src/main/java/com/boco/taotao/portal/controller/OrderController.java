package com.boco.taotao.portal.controller;

import com.boco.taotao.portal.service.CartService;
import com.boco.taotao.portal.vo.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Sheamus on 2018/3/21.
 */
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private CartService cartService;

    @RequestMapping("/order-cart")
    public String showOrderCart(HttpServletRequest request, HttpServletResponse response,Model model) {
        List<CartItem> cartItemList = cartService.getCartItemList(request, response);
        model.addAttribute("cartList",cartItemList);
        return "order-cart";
    }

}

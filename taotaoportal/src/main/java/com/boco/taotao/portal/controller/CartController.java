package com.boco.taotao.portal.controller;

import com.boco.taotao.portal.service.CartService;
import com.boco.taotao.portal.vo.CartItem;
import com.boco.taotao.vo.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

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
        return "redirect:/cart/success.html";//相对于根目录
    }

    @RequestMapping("/success")
    public String showSuccess() {
        return "cartSuccess";
    }

    @RequestMapping("/cart")
    public String showCartItems(HttpServletRequest request, HttpServletResponse response, Model model) {
        List<CartItem> cartItemList = cartService.getCartItemList(request, response);
        model.addAttribute("cartList",cartItemList);
        return "cart";
    }

    /**
     * 修改cookie中该商品的数量
     * @return
     */
    @RequestMapping("/update/{itemId}/{num}")
    @ResponseBody
    public TaotaoResult changeNum(HttpServletRequest request,
                                  HttpServletResponse response,
                                  @PathVariable Long itemId,
                                  @PathVariable Integer num) {
        return cartService.addCartItem(request,response,itemId,num);
    }

    /**
     * 从cookie中删除该商品
     * @param request
     * @param response
     * @param itemId
     * @return
     */
    @RequestMapping("/delete/{itemId}")
    public String deleteCartItem(HttpServletRequest request,
                                       HttpServletResponse response,
                                       @PathVariable Long itemId) {
        TaotaoResult result = cartService.deleteCartItem(request, response, itemId);
        return "redirect:/cart/cart.html";
    }

}

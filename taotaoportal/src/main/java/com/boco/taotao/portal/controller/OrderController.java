package com.boco.taotao.portal.controller;

import com.boco.taotao.pojo.TbUser;
import com.boco.taotao.portal.pojo.Order;
import com.boco.taotao.portal.service.CartService;
import com.boco.taotao.portal.service.OrderService;
import com.boco.taotao.portal.vo.CartItem;
import org.joda.time.DateTime;
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
    @Autowired
    private OrderService orderService;

    @RequestMapping("/order-cart")
    public String showOrderCart(HttpServletRequest request, HttpServletResponse response,Model model) {
        List<CartItem> cartItemList = cartService.getCartItemList(request, response);
        model.addAttribute("cartList",cartItemList);
        return "order-cart";
    }

    @RequestMapping("/create")
    public String createOrder(Order order, Model model, HttpServletRequest request) {
        try {
            //从拦截器中取出用户的信息
            //从Request中取用户信息
            TbUser user = (TbUser) request.getAttribute("user");
            //在order对象中补全用户信息
            order.setUserId(user.getId());
            order.setBuyerNick(user.getUsername());
            //调用服务
            String orderId = orderService.createOrder(order);
            model.addAttribute("orderId", orderId);
            model.addAttribute("payment", order.getPayment());
            model.addAttribute("date", new DateTime().plusDays(3).toString("yyyy-MM-dd"));
            return "success";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message", "创建订单出错。请稍后重试！");
            return "error/exception";
        }

    }

}

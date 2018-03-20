package com.boco.taotao.order.controller;

import com.boco.taotao.order.service.OrderService;
import com.boco.taotao.order.vo.Order;
import com.boco.taotao.vo.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Sheamus on 2018/3/20.
 */
@Controller
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult createOrder(@RequestBody Order order) {
        try {
            TaotaoResult result = orderService.createOrder(order, order.getOrderItems(), order.getOrderShipping());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.fail(e);
        }
    }

}

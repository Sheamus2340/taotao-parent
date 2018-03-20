package com.boco.taotao.portal.service;

import com.boco.taotao.portal.vo.CartItem;
import com.boco.taotao.vo.TaotaoResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by Sheamus on 2018/3/19.
 */
public interface CartService {
    /**
     * 向购物车(使用cookie实现)中添加商品
     * @param itemId
     * @param num
     * @return
     */
    TaotaoResult addCartItem(HttpServletRequest request, HttpServletResponse response,long itemId, int num);

    /**
     * 从cookie中获取购物车列表
     * @param request
     * @param response
     * @return
     */
    List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response);

    /**
     * 删除购物车中的某个商品
     * @param request
     * @param response
     * @param itemId
     * @return
     */
    TaotaoResult deleteCartItem(HttpServletRequest request, HttpServletResponse response,long itemId);
}

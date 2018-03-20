package com.boco.taotao.portal.service;

import com.boco.taotao.vo.TaotaoResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

}

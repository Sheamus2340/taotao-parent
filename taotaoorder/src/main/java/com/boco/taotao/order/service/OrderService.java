package com.boco.taotao.order.service;

import com.boco.taotao.pojo.TbOrder;
import com.boco.taotao.pojo.TbOrderItem;
import com.boco.taotao.pojo.TbOrderShipping;
import com.boco.taotao.vo.TaotaoResult;

import java.util.List;

/**
 * Created by Sheamus on 2018/3/20.
 */
public interface OrderService {

    /**
     * 创建订单
     * @param tbOrder 订单信息表
     * @param itemList 订单明细对应的商品列表
     * @param tbOrderShipping 物流表
     * @return
     */
    TaotaoResult createOrder(TbOrder tbOrder, List<TbOrderItem> itemList, TbOrderShipping tbOrderShipping);
}

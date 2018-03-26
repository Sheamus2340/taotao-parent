package com.boco.taotao.portal.pojo;

import com.boco.taotao.pojo.TbOrder;
import com.boco.taotao.pojo.TbOrderItem;
import com.boco.taotao.pojo.TbOrderShipping;

import java.util.List;

/**
 * Created by Sheamus on 2018/3/26.
 */
public class Order extends TbOrder {

    private List<TbOrderItem> orderItems;
    private TbOrderShipping orderShipping;
    public List<TbOrderItem> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<TbOrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    public TbOrderShipping getOrderShipping() {
        return orderShipping;
    }
    public void setOrderShipping(TbOrderShipping orderShipping) {
        this.orderShipping = orderShipping;
    }


}

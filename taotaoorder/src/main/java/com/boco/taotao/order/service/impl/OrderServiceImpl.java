package com.boco.taotao.order.service.impl;

import com.boco.taotao.mapper.TbOrderItemMapper;
import com.boco.taotao.mapper.TbOrderMapper;
import com.boco.taotao.mapper.TbOrderShippingMapper;
import com.boco.taotao.order.dao.JedisClient;
import com.boco.taotao.order.service.OrderService;
import com.boco.taotao.pojo.TbOrder;
import com.boco.taotao.pojo.TbOrderItem;
import com.boco.taotao.pojo.TbOrderShipping;
import com.boco.taotao.vo.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Sheamus on 2018/3/20.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private TbOrderMapper orderMapper;
    @Autowired
    private TbOrderItemMapper orderItemMapper;
    @Autowired
    private TbOrderShippingMapper orderShippingMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value(value = "#{propertyConfigure['ORDER_GEN_KEY']}")
    private String ORDER_GEN_KEY;
    @Value(value = "#{propertyConfigure['ORDER_INIT_ID']}")
    private String ORDER_INIT_ID;
    @Value(value = "#{propertyConfigure['ORDER_ITEM_GEN_KEY']}")
    private String ORDER_ITEM_GEN_KEY;

    @Override
    public TaotaoResult createOrder(TbOrder tbOrder, List<TbOrderItem> itemList, TbOrderShipping tbOrderShipping) {
        //1. 向订单表中插入一条记录
        // 获取一个订单号，并且补全订单的pojo
        if(StringUtils.isBlank(jedisClient.get(ORDER_GEN_KEY))) {
            jedisClient.set(ORDER_GEN_KEY,ORDER_INIT_ID);
        }
        long orderId = jedisClient.incr(ORDER_GEN_KEY);
        tbOrder.setOrderId(orderId + "");
        //状态：1：未付款；2：已付款；3：未发货；4：已发货；5：交易成功；6：交易关闭；
        tbOrder.setStatus(1);
        Date date = new Date();
        tbOrder.setCreateTime(date);
        tbOrder.setUpdateTime(date);
        //0：未评价；1：已评价；
        tbOrder.setBuyerRate(0);
        orderMapper.insert(tbOrder);
        //2. 插入订单明细
        for(TbOrderItem orderItem : itemList) {
            long orderDetailId = jedisClient.incr(ORDER_ITEM_GEN_KEY);
            orderItem.setId(orderDetailId + "");
            //添加订单号
            orderItem.setOrderId(orderId + "");
            orderItemMapper.insert(orderItem);
        }
        //3. 插入物流表
        tbOrderShipping.setOrderId(orderId + "");
        tbOrderShipping.setCreated(date);
        tbOrderShipping.setUpdated(date);
        orderShippingMapper.insert(tbOrderShipping);
        return TaotaoResult.ok(orderId);
    }
}

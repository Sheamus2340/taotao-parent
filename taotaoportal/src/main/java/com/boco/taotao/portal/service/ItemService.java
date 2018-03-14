package com.boco.taotao.portal.service;

import com.boco.taotao.portal.pojo.ItemInfo;

/**
 * Created by Sheamus on 2018/3/14.
 */
public interface ItemService {
    /**
     * 通过ID查询商品的基本信息并且返回商品对象
     * @param itemId
     * @return
     */
    ItemInfo getItemById(long itemId);

    /**
     * 返回一个描述的html片段
     * @param itemId
     * @return
     */
    String getItemDescById(long itemId);

    /**
     * 请求rest服务，将返回的json转换成java对象
     * 将java对象里面的信息封装成html片段返回给前端页面
     * @param itemId
     * @return
     */
    String getItemParamById(long itemId);
}

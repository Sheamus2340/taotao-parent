package com.boco.taotao.service;

/**
 * 展示商品规格参数
 * Created by Sheamus on 2018/1/29.
 */
public interface ItemParamItemService {
    /**
     * 通过itemid查询商品规则参数
     * @param itemId
     * @return
     */
    String getItemParamByItemId(long itemId);
}

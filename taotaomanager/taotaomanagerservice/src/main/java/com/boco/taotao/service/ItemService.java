package com.boco.taotao.service;

import com.boco.taotao.pojo.TbItem;
import com.boco.taotao.vo.EUDataGridResult;
import com.boco.taotao.vo.TaotaoResult;

/**
 * 商品的service
 * Created by Sheamus on 2018/1/19.
 */
public interface ItemService {
    TbItem findItemById(Long itemId);

    /**
     * 分页查询商品数量
     * @param page 查询的页号
     * @param rows 每页的大小
     * @return
     */
    EUDataGridResult findItemsBypage(int page, int rows);

    /**
     * 创建商品
     * @param item
     * @return
     */
    TaotaoResult createItem(TbItem item,String desc,String itemParam) throws RuntimeException;
}

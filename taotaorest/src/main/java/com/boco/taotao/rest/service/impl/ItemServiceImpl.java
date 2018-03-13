package com.boco.taotao.rest.service.impl;

import com.boco.taotao.mapper.TbItemMapper;
import com.boco.taotao.pojo.TbItem;
import com.boco.taotao.rest.service.ItemService;
import com.boco.taotao.vo.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Sheamus on 2018/3/13.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    @Override
    public TaotaoResult getItemBaseInfo(long itemId) {
        TbItem item = itemMapper.selectByPrimaryKey(itemId);
        return TaotaoResult.ok(item);
    }
}

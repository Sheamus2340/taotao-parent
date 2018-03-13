package com.boco.taotao.rest.controller;

import com.boco.taotao.rest.service.ItemService;
import com.boco.taotao.vo.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品的信息
 * Created by Sheamus on 2018/3/13.
 */
@Controller
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/info/{itemId}")
    @ResponseBody
    public TaotaoResult getItemBaseInfo(@PathVariable("itemId") Long itemId) {
        TaotaoResult itemBaseInfo = itemService.getItemBaseInfo(itemId);
        return itemBaseInfo;
    }
}

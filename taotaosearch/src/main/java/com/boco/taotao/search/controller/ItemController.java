package com.boco.taotao.search.controller;

import com.boco.taotao.search.service.ItemService;
import com.boco.taotao.vo.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Sheamus on 2018/3/12.
 */
@Controller
@RequestMapping("/manager")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/importAll")
    @ResponseBody
    public TaotaoResult importAll() {
        return itemService.importAllItems();
    }

}

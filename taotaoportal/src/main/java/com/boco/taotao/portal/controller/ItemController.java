package com.boco.taotao.portal.controller;

import com.boco.taotao.portal.pojo.ItemInfo;
import com.boco.taotao.portal.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Sheamus on 2018/3/14.
 */
@Controller
public class ItemController {

    @Autowired
    private ItemService itemService;

    @RequestMapping("/item/{itemId}")
    public String showItem(@PathVariable Long itemId, Model model) {
        ItemInfo item = itemService.getItemById(itemId);
        //向model里面添加数据
        model.addAttribute("item",item);
        //返回逻辑视图
        return "item";
    }

}

package com.boco.taotao.controller;

import com.boco.taotao.service.ItemParamItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 展示商品规格参数
 * Created by Sheamus on 2018/1/29.
 */
@Controller
public class ItemParamItemController {

    @Autowired
    private ItemParamItemService itemParamItemService;

    /**
     * 返回JSP页面
     * @param itemId
     * @return
     */
    @RequestMapping("/showitem/{itemId}")
    public String showItemParam(@PathVariable Long itemId,Model model) {
        String itemParamByItemId = itemParamItemService.getItemParamByItemId(itemId);
        //把html放到域里面
        model.addAttribute("itemParam",itemParamByItemId);
        return "item";
    }

}

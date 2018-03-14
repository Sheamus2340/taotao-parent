package com.boco.taotao.portal.controller;

import com.boco.taotao.portal.pojo.ItemInfo;
import com.boco.taotao.portal.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    /**
     * 在HTTP协议里面解决乱码
     * @param itemId
     * @return
     */
    @RequestMapping(value = "/item/desc/{itemId}",produces = MediaType.TEXT_HTML_VALUE + ";charset=utf-8")
    @ResponseBody
    public String showItem(@PathVariable Long itemId) {
        String itemDescById = itemService.getItemDescById(itemId);
        return itemDescById;
    }

    /**
     * 在HTTP协议里面解决乱码
     * @param itemId
     * @return
     */
    @RequestMapping(value = "/item/param/{itemId}",produces = MediaType.TEXT_HTML_VALUE + ";charset=utf-8")
    @ResponseBody
    public String showParam(@PathVariable Long itemId) {
        String itemParamById = itemService.getItemParamById(itemId);
        return itemParamById;
    }

}

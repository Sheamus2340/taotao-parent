package com.boco.taotao.controller;

import com.boco.taotao.pojo.TbItem;
import com.boco.taotao.service.ItemService;
import com.boco.taotao.vo.EUDataGridResult;
import com.boco.taotao.vo.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Sheamus on 2018/1/19.
 */
@Controller
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 通过ID查询商品
     * @param itemId
     * @return
     */
    @RequestMapping("/{itemId}")
    @ResponseBody
    public TbItem findItemById(@PathVariable("itemId") Long itemId) {
        TbItem itemById = itemService.findItemById(itemId);
        return itemById;
    }

    /**
     * 分页查询商品
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping("/list")
    @ResponseBody
    public EUDataGridResult findItemsByPage(@RequestParam("page") Integer page,@RequestParam("rows") Integer rows) {
        return itemService.findItemsBypage(page,rows);
    }

    /**
     * 添加商品
     * @param item
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult saveItem(TbItem item,String desc,String itemParams) throws RuntimeException {
        return itemService.createItem(item,desc,itemParams);
    }

}

package com.boco.taotao.controller;

import com.boco.taotao.service.ItemCatService;
import com.boco.taotao.vo.EUTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 商品分类
 * Created by Sheamus on 2018/1/21.
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    @RequestMapping("/list")
    @ResponseBody
    public List<EUTreeNode> getTreeNodes(@RequestParam(value = "id",defaultValue = "0") Long parentId) {
        return itemCatService.getItemCatList(parentId);
    }

}

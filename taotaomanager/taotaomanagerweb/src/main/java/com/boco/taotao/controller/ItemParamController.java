package com.boco.taotao.controller;

import com.boco.taotao.pojo.TbItemParam;
import com.boco.taotao.service.ItemParamService;
import com.boco.taotao.vo.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 商品参数控制层
 * Created by Sheamus on 2018/1/29.
 */
@Controller
@RequestMapping("/item/param")
public class ItemParamController {
    @Autowired
    private ItemParamService itemParamService;

    @RequestMapping("/query/itemcatid/{itemCatId}")
    @ResponseBody
    public TaotaoResult getItemParamByCid(@PathVariable("itemCatId") Long itemCatId) {
        TaotaoResult itemParamByCid = itemParamService.getItemParamByCid(itemCatId);
        return itemParamByCid;
    }

    @RequestMapping("/save/{cid}")
    @ResponseBody
    public TaotaoResult insertItemParam(@PathVariable Long cid,String paramData) {
        TbItemParam itemParam = new TbItemParam();
        itemParam.setItemCatId(cid);
        itemParam.setParamData(paramData);
        TaotaoResult taotaoResult = itemParamService.insertItemParam(itemParam);
        return taotaoResult;
    }
}

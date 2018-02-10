package com.boco.taotao.rest.controller;

import com.boco.taotao.rest.service.ItemCatService;
import com.boco.taotao.rest.vo.CatResult;
import com.boco.taotao.vo.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Created by Sheamus on 2018/2/6.
 */
@Controller
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    /**
     * 告诉客户端返回的是JSON字符串，字符编码是UTF-8
     */
    @RequestMapping(value="/itemcat/list",produces = MediaType.APPLICATION_JSON_VALUE + ";charset=utf-8")
    @ResponseBody
    public String getItemCatList(@RequestParam String callback) {
        CatResult itemCatData = itemCatService.getItemCatData();
        //把VO转换成字符串
        String json = JsonUtils.objectToJson(itemCatData);
        //拼接成js
        String result = callback + "(" + json + ");";
        return result;
    }

    @RequestMapping(value="/itemcat/list2")
    @ResponseBody
    public MappingJacksonValue getItemCatList2(@RequestParam String callback) {
        CatResult itemCatData = itemCatService.getItemCatData();
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(itemCatData);
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
    }

}

package com.boco.taotao.portal.controller;

import com.boco.taotao.portal.service.ContentService;
import com.boco.taotao.vo.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Sheamus on 2018/2/6.
 */
@Controller
public class IndexController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/index")
    public String showIndex(Model model) {
        String adJson = contentService.getContentList();
        //广告位的数据
        model.addAttribute("ad1",adJson);
        return "index";
    }

    @RequestMapping(value = "/httpclient/post" , method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult testPost() {
        return TaotaoResult.ok();
    }

}

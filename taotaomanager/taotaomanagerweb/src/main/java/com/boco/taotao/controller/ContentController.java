package com.boco.taotao.controller;

import com.boco.taotao.service.ContentService;
import com.boco.taotao.vo.EUDataGridResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Sheamus on 2018/2/7.
 */
@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/query/list")
    @ResponseBody
    public EUDataGridResult getContentList(Long categoryId,
                                           @RequestParam("page") Integer page,
                                           @RequestParam("rows") Integer rows) {
        return contentService.findContentsBypageAndCategoryId(categoryId, page, rows);
    }
}

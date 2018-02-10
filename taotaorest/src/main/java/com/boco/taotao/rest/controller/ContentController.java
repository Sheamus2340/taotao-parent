package com.boco.taotao.rest.controller;

import com.boco.taotao.pojo.TbContent;
import com.boco.taotao.rest.service.ContentService;
import com.boco.taotao.util.ExceptionUtil;
import com.boco.taotao.vo.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Sheamus on 2018/2/7.
 */
@Controller
@RequestMapping("/content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @RequestMapping("/list/{categoryId}")
    @ResponseBody
    public TaotaoResult getContentByCategoryId(@PathVariable Long categoryId) {
        try {
            List<TbContent> list = contentService.getContentByCategoryId(categoryId);
            return TaotaoResult.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }

    }

}

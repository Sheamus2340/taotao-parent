package com.boco.taotao.controller;

import com.boco.taotao.service.ContentCategoryService;
import com.boco.taotao.vo.EUTreeNode;
import com.boco.taotao.vo.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by Sheamus on 2018/2/7.
 */
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

    @Autowired
    private ContentCategoryService contentCategoryService;

    @RequestMapping(value = "/list")
    @ResponseBody
    public List<EUTreeNode> getContentCategoryList(@RequestParam(value = "id",defaultValue = "0") Long parentId) {
        return contentCategoryService.getCategoryList(parentId);
    }

    /**
     * @param parentId 父节点的id
     * @param name 本节点的名称
     * @return
     */
    @RequestMapping(value = "/create" , method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult createContentCategory(@RequestParam(value = "parentId",defaultValue = "0")Long parentId,
                                              @RequestParam(value = "name",defaultValue = "")String name) {
        return contentCategoryService.createContentCategory(parentId,name);
    }

    @RequestMapping(value = "/delete" , method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult deleteContentCategory(Long id) {
        return contentCategoryService.deleteContentCategory(id);
    }

    @RequestMapping(value = "/update" , method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult updateContentCategory(Long id,String name) {
        return contentCategoryService.updateContentCategory(id,name);
    }
}

package com.boco.taotao.search.controller;

import com.boco.taotao.search.service.SearchService;
import com.boco.taotao.search.vo.SearchResult;
import com.boco.taotao.vo.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Sheamus on 2018/3/12.
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/query",method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult search(@RequestParam("q") String queryString,
                               @RequestParam(defaultValue = "1") Integer page,
                               @RequestParam(defaultValue = "60") Integer rows) {
        if(StringUtils.isBlank(queryString)) {
            return TaotaoResult.build(400,"没有查询条件");
        }
        try {
            //解决乱码问题
            queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
            SearchResult search = searchService.search(queryString, page, rows);
            return TaotaoResult.ok(search);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.fail(e);
        }
    }

}

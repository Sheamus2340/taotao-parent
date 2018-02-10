package com.boco.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Sheamus on 2018/1/19.
 */
@Controller
public class PageController {
    /**
     * 首页跳转
     * @return
     */
    @RequestMapping("/")
    public String pageIndex() {
        return "index";
    }

    /**
     * 其他页面跳转
     * @param page
     * @return
     */
    @RequestMapping("/{page}")
    public String pageJump(@PathVariable String page) {
        return page;
    }
}

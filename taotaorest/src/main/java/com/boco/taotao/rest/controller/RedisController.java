package com.boco.taotao.rest.controller;

import com.boco.taotao.rest.service.RedisService;
import com.boco.taotao.vo.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 缓存同步的controller
 * Created by Sheamus on 2018/2/10.
 */
@Controller
@RequestMapping("/cache/sync")
public class RedisController {
    @Autowired
    private RedisService redisService;

    @RequestMapping("/content/{categoryid}")
    @ResponseBody
    public TaotaoResult syncContentCache(@PathVariable Long categoryid) {
        return redisService.syncContent(categoryid);
    }
}

package com.boco.taotao.sso.controller;

import com.boco.taotao.sso.service.UserService;
import com.boco.taotao.vo.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Sheamus on 2018/3/15.
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 通过callback判断是否要进行jsonp的转换
     * @param content
     * @param type
     * @param callback
     * @return
     */
    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public Object checkData(@PathVariable(value = "param") String content,
                                  @PathVariable(value = "type") Integer type,
                                  String callback) {
        TaotaoResult result = null;
        //对参数进行校验，例如:type必须是1,2,3中的一个
        if(StringUtils.isBlank(content)) {
            result = TaotaoResult.build(400,"校验内容不能为空！");
        }
        if(type == null ) {
            result = TaotaoResult.build(400,"校验内容类型不能为空！");
        }
        if(type != 1 && type != 2 && type != 3) {
            result = TaotaoResult.build(400,"校验内容类型范围不正确！");
        }
        //判断是否验证出错
        if(result != null) {
            return getJsonpObject(callback, result);
        }
        //验证通过
        try {
            result = userService.checkData(content, type);
        } catch (Exception e) {
            e.printStackTrace();
            result = TaotaoResult.fail(e);
        }
        //需要支持Jsonp,通过是否有callback参数来判断是否要支持jsonp
        return getJsonpObject(callback, result);
    }

    /**
     * 判断是否要返回jsonp对象
     * @param callback
     * @param result
     * @return
     */
    private Object getJsonpObject(String callback, TaotaoResult result) {
        //判断是否要Jsonp处理
        if(callback != null) {
            //jsonp处理
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
        return result;
    }

}

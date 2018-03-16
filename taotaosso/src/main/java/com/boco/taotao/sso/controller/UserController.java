package com.boco.taotao.sso.controller;

import com.boco.taotao.pojo.TbUser;
import com.boco.taotao.sso.service.UserService;
import com.boco.taotao.vo.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

    /**
     * 用户注册接口
     * @param user
     * @return
     */
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult registerUser(TbUser user) {
        try {
            TaotaoResult result = userService.createUser(user);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.fail(e);
        }
    }

    /**
     * 登录接口
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult login(String username,
                              String password,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        try {
            return userService.userLogin(username,password,request,response);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.fail(e);
        }
    }

    /**
     * 通过token来获取用户信息
     * @param token
     * @param callback
     * @return
     */
    @RequestMapping(value = "/token/{token}",method = RequestMethod.GET)
    @ResponseBody
    public Object getUserByToken(@PathVariable String token,String callback) {
        TaotaoResult result = null;
        try {
            result = userService.getUserByToken(token);
        } catch (Exception e) {
            e.printStackTrace();
            result = TaotaoResult.fail(e);
        }
        //判断callback是否为null
        if(StringUtils.isBlank(callback)) {
            //返回正常数据
            return result;
        }
        //否则使用JSONP
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
    }

    @RequestMapping(value = "/logout/{token}")
    @ResponseBody
    public Object logout(@PathVariable String token,String callback) {
        TaotaoResult result = null;
        try {
            result = userService.signOut(token);
        } catch (Exception e) {
            e.printStackTrace();
            result = TaotaoResult.fail(e);
        }
        //判断callback是否为null
        if(StringUtils.isBlank(callback)) {
            //返回正常数据
            return result;
        }
        //否则使用JSONP
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
        mappingJacksonValue.setJsonpFunction(callback);
        return mappingJacksonValue;
    }

}

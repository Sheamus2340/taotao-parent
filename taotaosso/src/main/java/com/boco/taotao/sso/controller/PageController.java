package com.boco.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转controller
 * Created by Sheamus on 2018/3/16.
 */
@Controller
@RequestMapping("/page")
public class PageController {

    @RequestMapping("/register")
    public String showRegister() {
        return "register";
    }

    @RequestMapping("/login")
    public String showLogin(String redirect,Model model) {
        //给页面传递这个url，让它知道是从哪个界面跳转过来的
        model.addAttribute("redirect",redirect);
        return "login";
    }

}

package com.boco.taotao.portal.interceptor;

import com.boco.taotao.pojo.TbUser;
import com.boco.taotao.portal.service.impl.UserServiceImpl;
import com.boco.taotao.util.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器
 * Created by Sheamus on 2018/3/16.
 */
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private UserServiceImpl userService;

    /**
     * 在Handler之前做的事情
     * 返回值决定这个Handler（用户真实请求）是否执行:
     *  true：执行；
     *  false：不执行；
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        //1. 判断用户是否登录，从cookie中取token，根据token调用用户信息
        String token = CookieUtils.getCookieValue(httpServletRequest, "TT_TOKEN");
        TbUser tbUser = userService.getUserByToken(token);
        if(tbUser == null) {
            //2. 如果取不到用户信息，就需要跳转到登录页面，并且把用户请求的URL作为参数传递给登录页面，返回false
            String url = userService.SSO_BASE_URL + userService.SSO_PAGE_LOGIN + "?redirect=" + httpServletRequest.getRequestURL();
            httpServletResponse.sendRedirect(url);
            return false;
        }
        //3. 如果取到用户信息就放行不拦截，返回true
        //把用户信息放入Request
        httpServletRequest.setAttribute("user", tbUser);
        return true;
    }

    /**
     * 在Handler之后返回ModelAndView之前
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 在Handler之后返回ModelAndView之后
     * @param httpServletRequest
     * @param httpServletResponse
     * @param o
     * @param e
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}

package com.boco.taotao.portal.service.impl;

import com.boco.taotao.pojo.TbUser;
import com.boco.taotao.portal.service.UserService;
import com.boco.taotao.util.HttpClientUtil;
import com.boco.taotao.vo.TaotaoResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 调用sso的接口
 * Created by Sheamus on 2018/3/16.
 */
@Service
public class UserServiceImpl implements UserService {

    @Value(value = "${SSO_BASE_URL}")
    public String SSO_BASE_URL;
    @Value(value = "${SSO_USER_TOKEN}")
    private String SSO_USER_TOKEN;
    @Value(value = "${SSO_PAGE_LOGIN}")
    public String SSO_PAGE_LOGIN;//由于自容器不能访问父容器的属性，所以这里把属性定义为公开的，UserServiceImpl能访问的

    @Override
    public TbUser getUserByToken(String token) {
       try {
           String url = SSO_BASE_URL + SSO_USER_TOKEN + token;
           String json = HttpClientUtil.doGet(url);
           TaotaoResult result = TaotaoResult.formatToPojo(json, TbUser.class);
           if(result.getStatus() == 200) {
               //成功
               TbUser tbUser = (TbUser) result.getData();
               return tbUser;
           }
       } catch (Exception e) {
           e.printStackTrace();
       }
       //失败
       return null;
    }
}

package com.boco.taotao.sso.service;

import com.boco.taotao.pojo.TbUser;
import com.boco.taotao.vo.TaotaoResult;

/**
 * Created by Sheamus on 2018/3/15.
 */
public interface UserService {
    /**
     * 数据验证
     * @param content
     * @param type
     * @return
     */
    TaotaoResult checkData(String content,Integer type);

    /**
     * 用户注册
     * @param user
     * @return
     */
    TaotaoResult createUser(TbUser user);

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    TaotaoResult userLogin(String username,String password);

    /**
     * 根据token获取用户信息
     * @param token
     * @return
     */
    TaotaoResult getUserByToken(String token);

    /**
     * 根据token退出系统
     * @param token
     * @return
     */
    TaotaoResult signOut(String token);
}

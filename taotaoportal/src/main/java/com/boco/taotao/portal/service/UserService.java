package com.boco.taotao.portal.service;

import com.boco.taotao.pojo.TbUser;

/**
 * Created by Sheamus on 2018/3/16.
 */
public interface UserService {
    TbUser getUserByToken(String token);
}

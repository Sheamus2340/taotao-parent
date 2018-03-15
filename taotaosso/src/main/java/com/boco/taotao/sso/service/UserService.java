package com.boco.taotao.sso.service;

import com.boco.taotao.vo.TaotaoResult;

/**
 * Created by Sheamus on 2018/3/15.
 */
public interface UserService {
    TaotaoResult checkData(String content,Integer type);
}

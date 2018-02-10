package com.boco.taotao.rest.service;

import com.boco.taotao.vo.TaotaoResult;

/**
 * Created by Sheamus on 2018/2/10.
 */
public interface RedisService {
    TaotaoResult syncContent(long categoryId);
}

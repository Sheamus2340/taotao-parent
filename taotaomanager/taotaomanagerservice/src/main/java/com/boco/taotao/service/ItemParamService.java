package com.boco.taotao.service;

import com.boco.taotao.pojo.TbItemParam;
import com.boco.taotao.vo.TaotaoResult;

/**
 * 商品规格参数模板
 * Created by Sheamus on 2018/1/29.
 */
public interface ItemParamService {
    TaotaoResult getItemParamByCid(long cid);
    TaotaoResult insertItemParam(TbItemParam itemParam);
}

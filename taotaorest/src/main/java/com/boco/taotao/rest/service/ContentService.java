package com.boco.taotao.rest.service;

import com.boco.taotao.pojo.TbContent;

import java.util.List;

/**
 * Created by Sheamus on 2018/2/7.
 */
public interface ContentService {
    /**
     * 通过分类Id查询内容
     * @param categoryId
     * @return
     */
    List<TbContent> getContentByCategoryId(long categoryId);
}

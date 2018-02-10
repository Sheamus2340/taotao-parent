package com.boco.taotao.service;

import com.boco.taotao.pojo.TbContent;
import com.boco.taotao.vo.EUDataGridResult;
import com.boco.taotao.vo.TaotaoResult;

/**
 * Created by Sheamus on 2018/2/7.
 */
public interface ContentService {
    /**
     * 通过CategoryId分页获取content的内容
     * @param category
     * @param page
     * @param rows
     * @return
     */
    EUDataGridResult findContentsBypageAndCategoryId(long category,int page, int rows);

    /**
     * 内容数据的添加
     * @param content
     * @return
     */
    TaotaoResult insertContent(TbContent content);
}

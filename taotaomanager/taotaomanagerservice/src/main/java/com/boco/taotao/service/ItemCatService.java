package com.boco.taotao.service;

import com.boco.taotao.vo.EUTreeNode;

import java.util.List;

/**
 * 商品列表分类
 * Created by Sheamus on 2018/1/21.
 */
public interface ItemCatService {
    List<EUTreeNode> getItemCatList(long parentId);
}

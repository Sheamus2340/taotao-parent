package com.boco.taotao.service;

import com.boco.taotao.vo.EUTreeNode;
import com.boco.taotao.vo.TaotaoResult;

import java.util.List;

/**
 * Created by Sheamus on 2018/2/7.
 */
public interface ContentCategoryService {
    /**
     * 根据父id查询分类的节点
     * @param parentId
     * @return
     */
    List<EUTreeNode> getCategoryList(long parentId);

    /**
     * 创建子节点，返回子节点的数据
     * @param parentId
     * @param name
     * @return
     */
    TaotaoResult createContentCategory(long parentId, String name);

    /**
     * 删除内容分类
     * @param id
     * @return
     */
    TaotaoResult deleteContentCategory(long id);

    /**
     * 更新内容分类
     * @param id
     * @param name
     * @return
     */
    TaotaoResult updateContentCategory(Long id, String name);
}

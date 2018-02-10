package com.boco.taotao.service.impl;

import com.boco.taotao.mapper.TbContentCategoryMapper;
import com.boco.taotao.pojo.TbContentCategory;
import com.boco.taotao.pojo.TbContentCategoryExample;
import com.boco.taotao.service.ContentCategoryService;
import com.boco.taotao.vo.EUTreeNode;
import com.boco.taotao.vo.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sheamus on 2018/2/7.
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Autowired
    private TbContentCategoryMapper contentCategoryMapper;

    @Override
    public List<EUTreeNode> getCategoryList(long parentId) {
        TbContentCategoryExample contentCategoryExample = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = contentCategoryExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> tbContentCategories = contentCategoryMapper.selectByExample(contentCategoryExample);
        List<EUTreeNode> result = new ArrayList<>();
        for (TbContentCategory category : tbContentCategories) {
            EUTreeNode node = new EUTreeNode();
            node.setId(category.getId());
            node.setText(category.getName());
            node.setState(category.getIsParent() ? "closed" : "open");
            result.add(node);
        }
        return result;
    }

    @Override
    public TaotaoResult createContentCategory(long parentId,String name) {
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setIsParent(false);
        contentCategory.setParentId(parentId);
        contentCategory.setName(name);
        contentCategory.setStatus(1);
        contentCategory.setCreated(new Date());
        contentCategory.setSortOrder(1);
        contentCategory.setUpdated(new Date());
        //添加记录，ID会返回到contentCategory对象中
        contentCategoryMapper.insert(contentCategory);
        //修改父节点的isParent修改为true
        TbContentCategory parentContentCategory = contentCategoryMapper.selectByPrimaryKey(parentId);
        Boolean isParent = parentContentCategory.getIsParent();
        if(!isParent) {
            parentContentCategory.setIsParent(true);
            contentCategoryMapper.updateByPrimaryKey(parentContentCategory);
        }
        return TaotaoResult.ok(contentCategory);
    }

    @Override
    public TaotaoResult deleteContentCategory(long id) {
        long parentId = contentCategoryMapper.selectByPrimaryKey(id).getParentId();
        contentCategoryMapper.deleteByPrimaryKey(id);
        TbContentCategoryExample contentCategoryExample = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = contentCategoryExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbContentCategory> tbContentCategories = contentCategoryMapper.selectByExample(contentCategoryExample);
        //当父节点下没有了子节点
        if(tbContentCategories.size() == 0) {
            TbContentCategory contentCategory = new TbContentCategory();
            contentCategory.setId(parentId);
            contentCategory.setIsParent(false);
            contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult updateContentCategory(Long id, String name) {
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setId(id);
        contentCategory.setName(name);
        contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
        return TaotaoResult.ok();
    }

}

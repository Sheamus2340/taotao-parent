package com.boco.taotao.service.impl;

import com.boco.taotao.mapper.TbItemCatMapper;
import com.boco.taotao.pojo.TbItemCat;
import com.boco.taotao.pojo.TbItemCatExample;
import com.boco.taotao.service.ItemCatService;
import com.boco.taotao.vo.EUTreeNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sheamus on 2018/1/21.
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    /**
     * 通过父id获取该节点下的元素（只是一层）
     * @param parentId
     * @return
     */
    @Override
    public List<EUTreeNode> getItemCatList(long parentId) {
        TbItemCatExample itemCatExample = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = itemCatExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> tbItemCats = itemCatMapper.selectByExample(itemCatExample);
        List<EUTreeNode> nodes = transformToTreeNodes(tbItemCats);
        return nodes;
    }

    /**
     * 把TbItemCat装换成EUTreeNode
     * @param tbItemCats
     * @return
     */
    private List<EUTreeNode> transformToTreeNodes(List<TbItemCat> tbItemCats) {
        List<EUTreeNode> nodes = new ArrayList<>();
        if(tbItemCats != null && tbItemCats.size() != 0) {
            for(TbItemCat cat : tbItemCats) {
                EUTreeNode node = new EUTreeNode();
                node.setId(cat.getId());
                node.setText(cat.getName());
                node.setState(cat.getIsParent() ? "closed" : "open");
                nodes.add(node);
            }
        }
        return nodes;
    }
}

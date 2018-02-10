package com.boco.taotao.rest.service.impl;

import com.boco.taotao.mapper.TbItemCatMapper;
import com.boco.taotao.pojo.TbItemCat;
import com.boco.taotao.pojo.TbItemCatExample;
import com.boco.taotao.rest.service.ItemCatService;
import com.boco.taotao.rest.vo.CatNode;
import com.boco.taotao.rest.vo.CatResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sheamus on 2018/2/6.
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Autowired
    private TbItemCatMapper itemCatMapper;

    @Override
    public CatResult getItemCatData() {
        CatResult catResult = new CatResult();
        catResult.setData(getItemCatList(0));
        return catResult;
    }

    /**
     * 递归的查询所有的节点
     * @param parentId
     * @return
     */
    private List<?> getItemCatList(long parentId) {
        TbItemCatExample itemCatExample = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = itemCatExample.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        List<TbItemCat> tbItemCats = itemCatMapper.selectByExample(itemCatExample);
        List result = new ArrayList();
        //添加计数器
        int count = 0;
        //遍历所有的子节点
        for (TbItemCat itemCat : tbItemCats) {
            //当是父节点的时候
            if(itemCat.getIsParent()) {
                CatNode catNode = new CatNode();
                if(itemCat.getParentId() == 0) {
                    /**
                        第一层节点的格式：
                        "u": "/products/1.html",
                        "n": "<a href='/products/1.html'>图书、音像、电子书刊</a>"
                    */
                    catNode.setName("<a href='/products/" + itemCat.getId() + ".html'>" + itemCat.getName() + "</a>");
                } else {
                    /**
                     * 第二层的时候
                     *"u": "/products/2.html",
                      "n": "电子书刊",
                     */
                    catNode.setName(itemCat.getName());
                }
                catNode.setUrl("/products/" + itemCat.getId() + ".html");
                catNode.setItem(getItemCatList(itemCat.getId()));
                result.add(catNode);
                //判断大于14的时候不再遍历
                count++;
                //第一层只取14个
                if(itemCat.getParentId() == 0 && count >= 14) {
                    break;
                }
            } else {
                //当是叶子节点的时候: "/products/3.html|电子书",
                result.add("/products/" + itemCat.getId() + ".html|" + itemCat.getName());
            }

        }
        return result;
    }

}

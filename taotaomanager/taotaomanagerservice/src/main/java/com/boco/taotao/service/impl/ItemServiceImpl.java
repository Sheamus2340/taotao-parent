package com.boco.taotao.service.impl;

import com.boco.taotao.mapper.TbItemDescMapper;
import com.boco.taotao.mapper.TbItemMapper;
import com.boco.taotao.mapper.TbItemParamItemMapper;
import com.boco.taotao.pojo.TbItem;
import com.boco.taotao.pojo.TbItemDesc;
import com.boco.taotao.pojo.TbItemExample;
import com.boco.taotao.pojo.TbItemParamItem;
import com.boco.taotao.service.ItemService;
import com.boco.taotao.util.IDUtils;
import com.boco.taotao.vo.EUDataGridResult;
import com.boco.taotao.vo.TaotaoResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Sheamus on 2018/1/19.
 */
@Service
public class ItemServiceImpl implements ItemService {

    //按照查找类型注入
    @Autowired
    private TbItemMapper itemMapper;
    //商品描述的mapper
    @Autowired
    private TbItemDescMapper itemDescMapper;
    //商品规格参数保存
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Override
    public TbItem findItemById(Long itemId) {
        // 1. 方法一、直接通过 Mybatis Generator生成的Mapper提供的 selectByPrimaryKey 方法实现
        //TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
        //return tbItem;

        // 2. 方法二、使用 Mybatis Generator生成的Mapper提供的
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(itemId);
        List<TbItem> tbItems = itemMapper.selectByExample(example);
        if(tbItems != null && tbItems.size() > 0) {
            TbItem tbItem = tbItems.get(0);
            return tbItem;
        }
        return null;
    }

    /**
     * 分页查询商品的数量
     * @param page 查询的页号
     * @param rows 每页的大小
     * @return
     */
    @Override
    public EUDataGridResult findItemsBypage(int page, int rows) {
        TbItemExample example = new TbItemExample();
        //添加分页的代码
        PageHelper.startPage(page,rows);
        List<TbItem> tbItems = itemMapper.selectByExample(example);
        PageInfo<TbItem> pageInfo = new PageInfo<>(tbItems);
        //获取数据总量
        long total = pageInfo.getTotal();
        EUDataGridResult euDataGridResult = new EUDataGridResult(total,tbItems);
        return euDataGridResult;
    }

    /**
     * 生成商品
     * @param item
     * @return
     */
    @Override
    public TaotaoResult createItem(TbItem item,String desc,String itemParam) throws RuntimeException {
        //生成商品ID
        long itemId = IDUtils.genItemId();
        item.setId(itemId);
        //设置默认属性
        //商品的状态 1：正常，2：下架，3：删除
        item.setStatus((byte)1);
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //添加商品基本信息
        int insert = itemMapper.insert(item);
        //保存商品描述
        TaotaoResult taotaoResult = saveItemDesc(itemId, desc);
        if(taotaoResult.getStatus() != 200) {
            throw new RuntimeException();
        }
        //保存itemParam参数
        TaotaoResult taotaoResult1 = saveItemParamItem(itemId, itemParam);
        if(taotaoResult1.getStatus() != 200) {
            throw new RuntimeException();
        }
        return TaotaoResult.ok();
    }

    /**
     * 保存商品描述
     * @param desc
     * @return
     */
    private TaotaoResult saveItemDesc(Long itemId,String desc) {
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        itemDescMapper.insert(itemDesc);
        return TaotaoResult.ok();
    }

    /**
     * 保存商品规格参数
     * @param itemId
     * @param itemParam
     * @return
     */
    private TaotaoResult saveItemParamItem(Long itemId,String itemParam) {
        TbItemParamItem itemParamItem = new TbItemParamItem();
        itemParamItem.setItemId(itemId);
        itemParamItem.setParamData(itemParam);
        itemParamItem.setUpdated(new Date());
        itemParamItem.setCreated(new Date());
        itemParamItemMapper.insert(itemParamItem);
        return TaotaoResult.ok();
    }
}

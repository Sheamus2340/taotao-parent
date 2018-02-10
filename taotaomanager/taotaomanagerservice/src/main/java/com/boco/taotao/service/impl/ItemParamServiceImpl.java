package com.boco.taotao.service.impl;

import com.boco.taotao.mapper.TbItemParamMapper;
import com.boco.taotao.pojo.TbItemParam;
import com.boco.taotao.pojo.TbItemParamExample;
import com.boco.taotao.service.ItemParamService;
import com.boco.taotao.vo.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Sheamus on 2018/1/29.
 */
@Service
public class ItemParamServiceImpl implements ItemParamService {
    @Autowired
    private TbItemParamMapper itemParamMapper;

    @Override
    public TaotaoResult getItemParamByCid(long cid) {
        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(cid);
        List<TbItemParam> tbItemParams = itemParamMapper.selectByExampleWithBLOBs(example);
        //判断是否查询到结果
        if(tbItemParams != null && tbItemParams.size() > 0) {
            return TaotaoResult.ok(tbItemParams.get(0));
        }
        //没有查询到结果
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult insertItemParam(TbItemParam itemParam) {
        itemParam.setUpdated(new Date());
        itemParam.setCreated(new Date());
        itemParamMapper.insert(itemParam);
        return TaotaoResult.ok();
    }

}

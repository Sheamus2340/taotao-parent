package com.boco.taotao.service.impl;

import com.boco.taotao.mapper.TbContentMapper;
import com.boco.taotao.pojo.TbContent;
import com.boco.taotao.pojo.TbContentExample;
import com.boco.taotao.service.ContentService;
import com.boco.taotao.vo.EUDataGridResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Sheamus on 2018/2/7.
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;

    @Override
    public EUDataGridResult findContentsBypageAndCategoryId(long category, int page, int rows) {
        TbContentExample contentExample = new TbContentExample();
        //添加分页的代码
        PageHelper.startPage(page,rows);
        TbContentExample.Criteria criteria = contentExample.createCriteria();
        criteria.andCategoryIdEqualTo(category);
        List<TbContent> tbContents = contentMapper.selectByExample(contentExample);
        PageInfo<TbContent> pageInfo = new PageInfo<>(tbContents);
        //获取数据总量
        long total = pageInfo.getTotal();
        EUDataGridResult euDataGridResult = new EUDataGridResult(total,tbContents);
        return euDataGridResult;
    }
}

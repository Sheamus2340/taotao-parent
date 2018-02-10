package com.boco.taotao.rest.service.impl;

import com.boco.taotao.mapper.TbContentMapper;
import com.boco.taotao.pojo.TbContent;
import com.boco.taotao.pojo.TbContentExample;
import com.boco.taotao.rest.service.ContentService;
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
    public List<TbContent> getContentByCategoryId(long categoryId) {
        TbContentExample contentExample = new TbContentExample();
        TbContentExample.Criteria criteria = contentExample.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> tbContents = contentMapper.selectByExample(contentExample);
        return tbContents;
    }
}

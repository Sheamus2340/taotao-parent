package com.boco.taotao.service.impl;

import com.boco.taotao.mapper.TbContentMapper;
import com.boco.taotao.pojo.TbContent;
import com.boco.taotao.pojo.TbContentExample;
import com.boco.taotao.service.ContentService;
import com.boco.taotao.util.HttpClientUtil;
import com.boco.taotao.vo.EUDataGridResult;
import com.boco.taotao.vo.TaotaoResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 内容后台的管理
 * Created by Sheamus on 2018/2/7.
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;
   /* @Value("${REST_BASE_URL}")*/
    @Value(value="#{propertyConfigure['REST_BASE_URL']}")
    private String REST_BASE_URL;
    /*@Value("${REST_CONTENT_SYNC_URL}")*/
    @Value(value="#{propertyConfigure['REST_CONTENT_SYNC_URL']}")
    private String REST_CONTENT_SYNC_URL;

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

    @Override
    public TaotaoResult insertContent(TbContent content) {
        //补全pojo内容
        content.setCreated(new Date());
        content.setUpdated(new Date());
        contentMapper.insert(content);

        //添加缓存同步逻辑
        try {
            HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getCategoryId());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return TaotaoResult.ok();
    }
}

package com.boco.taotao.rest.service.impl;

import com.boco.taotao.mapper.TbContentMapper;
import com.boco.taotao.pojo.TbContent;
import com.boco.taotao.pojo.TbContentExample;
import com.boco.taotao.rest.dao.JedisClient;
import com.boco.taotao.rest.service.ContentService;
import com.boco.taotao.vo.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Sheamus on 2018/2/7.
 */
@Service
public class ContentServiceImpl implements ContentService {

    @Autowired
    private TbContentMapper contentMapper;
    @Autowired
    private JedisClient jedisClient;
    /*@Value("${INDEX_CONTENT_REDIS_KEY}")*/
    @Value(value="#{propertyConfigure['INDEX_CONTENT_REDIS_KEY']}")
    private String INDEX_CONTENT_REDIS_KEY;

    @Override
    public List<TbContent> getContentByCategoryId(long categoryId) {
        //增加缓存 -- 注意：增加缓存不能影响之前的代码逻辑，即缓存出错不影响整个程序的运行
        //先从查询缓存中是否存在要查询的数据
        try {
            String result = jedisClient.hget(INDEX_CONTENT_REDIS_KEY, categoryId + "");
            //有直接返回
            if (!StringUtils.isBlank(result)) {
                //把字符串转换成list
                List<TbContent> resultList = JsonUtils.jsonToList(result, TbContent.class);
                return resultList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //没有然后再查询数据库
        TbContentExample contentExample = new TbContentExample();
        TbContentExample.Criteria criteria = contentExample.createCriteria();
        criteria.andCategoryIdEqualTo(categoryId);
        List<TbContent> tbContents = contentMapper.selectByExample(contentExample);
        //查询完数据库后再把数据添加到缓存中
        try {
            //把list转换成字符串
            String cacheString = JsonUtils.objectToJson(tbContents);
            jedisClient.hset(INDEX_CONTENT_REDIS_KEY, categoryId + "", cacheString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tbContents;
    }
}

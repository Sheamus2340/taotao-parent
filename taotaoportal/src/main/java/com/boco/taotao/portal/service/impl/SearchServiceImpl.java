package com.boco.taotao.portal.service.impl;

import com.boco.taotao.portal.service.SearchService;
import com.boco.taotao.portal.vo.SearchResult;
import com.boco.taotao.util.HttpClientUtil;
import com.boco.taotao.vo.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品搜索service
 * Created by Sheamus on 2018/3/13.
 */
@Service
public class SearchServiceImpl implements SearchService {
    private static final Logger logger = LoggerFactory.getLogger(SearchServiceImpl.class);

    @Value("${SEARCH_BASE_URL}")
    private String SEARCH_BASE_URL;

    @Override
    public SearchResult search(String queryString, int page) {
        Map<String,String> param = new HashMap<>();
        param.put("q",queryString);
        param.put("page",page + "");
        param.put("rows",60 + "");
        try {
            String json = HttpClientUtil.doGet(SEARCH_BASE_URL,param);
            //当solr索引库总不存在的时候不要抛控制指针异常
            if(!StringUtils.isBlank(json)) {
                TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, SearchResult.class);
                //成功
                if(taotaoResult.getStatus() == 200) {
                    SearchResult result = (SearchResult)taotaoResult.getData();
                    return result;
                }
            } else {
                logger.warn("solr索引库中不存在{}的商品",queryString);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return new SearchResult();
    }
}

package com.boco.taotao.portal.service.impl;

import com.boco.taotao.portal.service.SearchService;
import com.boco.taotao.portal.vo.SearchResult;
import com.boco.taotao.util.HttpClientUtil;
import com.boco.taotao.vo.TaotaoResult;
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

    @Value("${SEARCH_BASE_URL}")
    private String SEARCH_BASE_URL;

    @Override
    public SearchResult search(String queryString, int page) {
        Map<String,String> param = new HashMap<>();
        param.put("q",queryString);
        param.put("page",page + "");
        try {
            String json = HttpClientUtil.doGet(SEARCH_BASE_URL,param);
            TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, SearchResult.class);
            //成功
            if(taotaoResult.getStatus() == 200) {
                SearchResult result = (SearchResult)taotaoResult.getData();
                return result;
            }
        } catch(Exception e) {
            e.printStackTrace();

        }
        return null;
    }
}

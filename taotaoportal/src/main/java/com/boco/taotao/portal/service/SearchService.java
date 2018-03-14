package com.boco.taotao.portal.service;

import com.boco.taotao.portal.vo.SearchResult;

/**
 * Created by Sheamus on 2018/3/13.
 */
public interface SearchService {
    /**
     * 调用taotao-search模块的服务完成请求
     * @param queryString
     * @param page
     * @return
     */
    SearchResult search(String queryString,int page);
}

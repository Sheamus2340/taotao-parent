package com.boco.taotao.search.service;

import com.boco.taotao.search.vo.SearchResult;

/**
 * Created by Sheamus on 2018/3/13.
 */
public interface SearchService {
    SearchResult search(String queryString, int page, int rows) throws Exception;
}

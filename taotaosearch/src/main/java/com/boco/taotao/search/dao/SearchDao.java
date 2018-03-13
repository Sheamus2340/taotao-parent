package com.boco.taotao.search.dao;

import com.boco.taotao.search.vo.SearchResult;
import org.apache.solr.client.solrj.SolrQuery;

/**
 * Created by Sheamus on 2018/3/13.
 */
public interface SearchDao {
    SearchResult search(SolrQuery query) throws Exception;
}

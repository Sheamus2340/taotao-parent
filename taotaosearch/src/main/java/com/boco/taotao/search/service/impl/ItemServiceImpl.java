package com.boco.taotao.search.service.impl;

import com.boco.taotao.search.mapper.ItemMapper;
import com.boco.taotao.search.pojo.Item;
import com.boco.taotao.search.service.ItemService;
import com.boco.taotao.vo.TaotaoResult;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Sheamus on 2018/3/12.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private SolrClient solrClient;

    @Override
    public TaotaoResult importAllItems() {
        //查询出所有商品
        List<Item> itemLists = itemMapper.getItemLists();
        //把所有商品写入索引库
        for (Item item : itemLists) {
            SolrInputDocument solrInputDocument = new SolrInputDocument();
            solrInputDocument.setField("id", item.getId());
            solrInputDocument.setField("item_title", item.getTitle());
            solrInputDocument.setField("item_sell_point", item.getSell_point());
            solrInputDocument.setField("item_price", item.getPrice());
            solrInputDocument.setField("item_image", item.getImage());
            solrInputDocument.setField("item_category_name", item.getCategory_name());
            solrInputDocument.setField("item_desc", item.getItem_des());
            //写入索引库
            try {
                solrClient.add(solrInputDocument);
                solrClient.commit();
            } catch (Exception e) {
                e.printStackTrace();
                TaotaoResult.fail(e);
            }
        }
        return TaotaoResult.ok();
    }
}

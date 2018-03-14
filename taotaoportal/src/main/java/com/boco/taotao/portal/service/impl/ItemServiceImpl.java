package com.boco.taotao.portal.service.impl;

import com.boco.taotao.portal.pojo.ItemInfo;
import com.boco.taotao.portal.service.ItemService;
import com.boco.taotao.util.HttpClientUtil;
import com.boco.taotao.vo.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 商品的ID
 * Created by Sheamus on 2018/3/14.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Value("${ITEM_BASE_URL}")
    private String ITEM_BASE_URL;
    @Value("${ITEM_INFO_URL}")
    private String ITEM_INFO_URL;

    @Override
    public ItemInfo getItemById(long itemId) {
        try {
            //调用rest服务
            String url = ITEM_BASE_URL + ITEM_INFO_URL + itemId;
            String json = HttpClientUtil.doGet(url);
            if(!StringUtils.isBlank(json)) {
                TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, ItemInfo.class);
                if(taotaoResult.getStatus() == 200) {
                    ItemInfo itemInfo = (ItemInfo) taotaoResult.getData();
                    return itemInfo;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

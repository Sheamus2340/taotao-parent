package com.boco.taotao.portal.service.impl;

import com.boco.taotao.pojo.TbContent;
import com.boco.taotao.portal.service.ContentService;
import com.boco.taotao.portal.vo.ADResult;
import com.boco.taotao.util.HttpClientUtil;
import com.boco.taotao.vo.JsonUtils;
import com.boco.taotao.vo.TaotaoResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sheamus on 2018/2/7.
 */
@Service
public class ContentServiceImpl implements ContentService {
    private static final Logger logger = LoggerFactory.getLogger(ContentServiceImpl.class);

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;
    @Value("${REST_INDEX_AD_URL}")
    private String REST_INDEX_AD_URL;

    @Override
    public String getContentList() {
        logger.info("---------------- > {}{}",REST_BASE_URL,REST_INDEX_AD_URL);
        //调用rest的服务，并且将JSON数据转换成bean
        String result = HttpClientUtil.doGet(REST_BASE_URL + REST_INDEX_AD_URL);
        try {
            TaotaoResult taotaoResult = TaotaoResult.formatToList(result, TbContent.class);
            if(taotaoResult.getStatus() == 200) {
                //取数据
                List<TbContent> datas = (List<TbContent>) taotaoResult.getData();
                if(datas != null && datas.size() > 0) {
                    //转成页面需要的格式
                    List<ADResult> adResults = new ArrayList<>();
                    for(TbContent content : datas) {
                        ADResult adResult = new ADResult();
                        adResult.setSrc(content.getPic());
                        adResult.setHeight(240);
                        adResult.setWidth(670);
                        adResult.setAlt(content.getTitleDesc());
                        adResult.setSrcB(content.getPic2());
                        adResult.setHeightB(240);
                        adResult.setWidthB(550);
                        adResult.setHref(content.getUrl());
                        adResults.add(adResult);
                    }
                    return JsonUtils.objectToJson(adResults);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

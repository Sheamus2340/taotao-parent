package com.boco.taotao.portal.service.impl;

import com.boco.taotao.pojo.TbItemDesc;
import com.boco.taotao.pojo.TbItemParamItem;
import com.boco.taotao.portal.pojo.ItemInfo;
import com.boco.taotao.portal.service.ItemService;
import com.boco.taotao.util.HttpClientUtil;
import com.boco.taotao.vo.JsonUtils;
import com.boco.taotao.vo.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

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
    @Value("${ITEM_DESC_URL}")
    private String ITEM_DESC_URL;
    @Value("${ITEM_PARAM_URL}")
    private String ITEM_PARAM_URL;

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

    @Override
    public String getItemDescById(long itemId) {
        try {
            //调用rest服务
            String url = ITEM_BASE_URL + ITEM_DESC_URL + itemId;
            String json = HttpClientUtil.doGet(url);
            if(!StringUtils.isBlank(json)) {
                TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItemDesc.class);
                if(taotaoResult.getStatus() == 200) {
                    TbItemDesc tbItemDesc = (TbItemDesc) taotaoResult.getData();
                    String itemDesc = tbItemDesc.getItemDesc();
                    return itemDesc;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getItemParamById(long itemId) {
        try {
            //调用rest服务
            String url = ITEM_BASE_URL + ITEM_PARAM_URL + itemId;
            String json = HttpClientUtil.doGet(url);
            if(!StringUtils.isBlank(json)) {
                TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItemParamItem.class);
                if(taotaoResult.getStatus() == 200) {
                    TbItemParamItem itemParamItem = (TbItemParamItem) taotaoResult.getData();
                    //将数据封装成html对象
                    String html = encapsulationHtml(itemParamItem);
                    return html;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //出错返回一个空字符串
        return "";
    }

    /**
     * 将商品规则对象信息封装成html
     * @param itemParamItem
     * @return
     */
    private String encapsulationHtml(TbItemParamItem itemParamItem) {
        String paramData = itemParamItem.getParamData();
        //生成HTML
        //把规格参数JSON数据转换成java对象
        List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
        stringBuffer.append("    <tbody>\n");
        for (Map m1 : jsonList) {
            stringBuffer.append("        <tr>\n");
            stringBuffer.append("            <th class=\"tdTitle\" colspan=\"2\">" + m1.get("group") + "</th>\n");
            stringBuffer.append("        </tr>\n");
            List<Map> list2 = (List<Map>) m1.get("params");
            for (Map m2 : list2) {
                stringBuffer.append("        <tr>\n");
                stringBuffer.append("            <td class=\"tdTitle\">" + m2.get("k") + "</td>\n");
                stringBuffer.append("            <td>" + m2.get("v") + "</td>\n");
                stringBuffer.append("        </tr>\n");
            }
        } stringBuffer.append("    </tbody>\n");
        stringBuffer.append("</table>");
        return stringBuffer.toString();
    }


}

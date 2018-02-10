package com.boco.taotao.service.impl;

import com.boco.taotao.mapper.TbItemParamItemMapper;
import com.boco.taotao.pojo.TbItemParamItem;
import com.boco.taotao.pojo.TbItemParamItemExample;
import com.boco.taotao.service.ItemParamItemService;
import com.boco.taotao.vo.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 展示商品规格参数
 * Created by Sheamus on 2018/1/29.
 */
@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {

    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Override
    public String getItemParamByItemId(long itemId) {
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> tbItemParamItems = itemParamItemMapper.selectByExampleWithBLOBs(example);
        if(tbItemParamItems == null || tbItemParamItems.size() == 0) {
            return "";
        }
        //取规格参数信息
        TbItemParamItem itemParamItem = tbItemParamItems.get(0);
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

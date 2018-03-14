package com.boco.taotao.rest.service.impl;

import com.boco.taotao.mapper.TbItemDescMapper;
import com.boco.taotao.mapper.TbItemMapper;
import com.boco.taotao.mapper.TbItemParamItemMapper;
import com.boco.taotao.pojo.TbItem;
import com.boco.taotao.pojo.TbItemDesc;
import com.boco.taotao.pojo.TbItemParamItem;
import com.boco.taotao.pojo.TbItemParamItemExample;
import com.boco.taotao.rest.dao.JedisClient;
import com.boco.taotao.rest.service.ItemService;
import com.boco.taotao.vo.JsonUtils;
import com.boco.taotao.vo.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Sheamus on 2018/3/13.
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemDescMapper itemDescMapper;
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value(value="#{propertyConfigure['REDIS_ITEM_KEY']}")
    private String REDIS_ITEM_KEY;
    @Value(value="#{propertyConfigure['REDIS_ITEM_EXPIRE']}")
    private String REDIS_ITEM_EXPIRE;

    /**
     * 添加缓存逻辑，缓存的逻辑不能影响之前的业务
     * redis的hash是不支持过期时间的，所以要采用折中的方法来实现hash的效果
     * key的格式：
     *     大类:中类:小类
     *    这样在redis中的表现就会像目录一样，一层一层的
     *    |  - 大类
     *    |     - 中类
     *    |          - 小类
     * 商品:
     * 基本信息存法：
     *      REDIS_ITEM_KEY:商品id:base=json
     * 商品描述存法：
     *      REDIS_ITEM_KEY:商品id:desc=json
     * 规格参数：
     *      REDIS_ITEM_KEY:商品id:param=json
     *
     * @param itemId
     * @return
     */
    @Override
    public TaotaoResult getItemBaseInfo(long itemId) {
        //查询之前先看缓存是否存在
        try {
            String key = REDIS_ITEM_KEY + ":" + itemId + ":" + "base";
            String json = jedisClient.get(key);
            if(!StringUtils.isBlank(json)) {
                TbItem tbItem = JsonUtils.jsonToPojo(json, TbItem.class);
                return TaotaoResult.ok(tbItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TbItem item = itemMapper.selectByPrimaryKey(itemId);

        try {
            //把商品信息写入缓存
            String key = REDIS_ITEM_KEY + ":" + itemId + ":" + "base";
            jedisClient.set(key, JsonUtils.objectToJson(item));
            //设置key的过期时间
            jedisClient.expire(key,Integer.parseInt(REDIS_ITEM_EXPIRE));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TaotaoResult.ok(item);
    }

    /**
     * 商品详细信息添加缓存
     * @param itemId
     * @return
     */
    @Override
    public TaotaoResult getItemDescInfo(long itemId) {
        //先查询缓存
        try {
            String key = REDIS_ITEM_KEY + ":" + itemId + ":" + "desc";
            String json = jedisClient.get(key);
            if(!StringUtils.isBlank(json)) {
                TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return TaotaoResult.ok(itemDesc);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(itemId);

        //把查询结果录入缓存
        try {
            String key = REDIS_ITEM_KEY + ":" + itemId + ":" + "desc";
            jedisClient.set(key,JsonUtils.objectToJson(itemDesc));
            jedisClient.expire(key,Integer.parseInt(REDIS_ITEM_EXPIRE));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return TaotaoResult.ok(itemDesc);
    }

    /**
     * 商品规格参数
     * @param itemId
     * @return
     */
    @Override
    public TaotaoResult getItemParamInfo(long itemId) {
        //先查询缓存
        try {
            String key = REDIS_ITEM_KEY + ":" + itemId + ":" + "param";
            String json = jedisClient.get(key);
            if(!StringUtils.isBlank(json)) {
                TbItemParamItem itemParamItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
                return TaotaoResult.ok(itemParamItem);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> tbItemParamItems = itemParamItemMapper.selectByExampleWithBLOBs(example);
        if(tbItemParamItems != null && tbItemParamItems.size() != 0) {
            TbItemParamItem itemParamItem = tbItemParamItems.get(0);
            //把查询结果录入缓存
            try {
                String key = REDIS_ITEM_KEY + ":" + itemId + ":" + "param";
                jedisClient.set(key,JsonUtils.objectToJson(itemParamItem));
                jedisClient.expire(key,Integer.parseInt(REDIS_ITEM_EXPIRE));
            } catch (Exception e) {
                e.printStackTrace();
            }
            return TaotaoResult.ok(itemParamItem);
        }
        return TaotaoResult.build(400,"无此商品信息");
    }

}

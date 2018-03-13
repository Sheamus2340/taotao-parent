package com.boco.taotao.rest.service.impl;

import com.boco.taotao.rest.service.RedisService;
import com.boco.taotao.util.ExceptionUtil;
import com.boco.taotao.vo.TaotaoResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by Sheamus on 2018/2/10.
 */
@Service
public class RedisServiceImpl implements RedisService {

    /*@Autowired
    private JedisClientSingle jedisClient;*/

    @Value("${INDEX_CONTENT_REDIS_KEY}")
    private String INDEX_CONTENT_REDIS_KEY;

    @Override
    public TaotaoResult syncContent(long categoryId) {
        try {
            /*jedisClient.hdel(INDEX_CONTENT_REDIS_KEY, categoryId + "");*/
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok();
    }
}

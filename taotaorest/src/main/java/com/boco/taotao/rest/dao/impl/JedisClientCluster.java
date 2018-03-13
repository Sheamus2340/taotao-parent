package com.boco.taotao.rest.dao.impl;

import com.boco.taotao.rest.dao.JedisClient;
import redis.clients.jedis.JedisCluster;

/**
 * 用于集群的redis的对象
 * Created by Sheamus on 2018/2/10.
 */
public class JedisClientCluster implements JedisClient {
    //@Autowired
    private JedisCluster redisClient;

    @Override
    public String get(String key) {
        return redisClient.get(key);
    }

    @Override
    public String set(String key, String value) {
        return redisClient.set(key, value);
    }

    @Override
    public String hget(String hkey, String key) {
        return redisClient.hget(hkey, key);
    }

    @Override
    public long hset(String hkey, String key, String value) {
        return redisClient.hset(hkey, key, value);
    }

    @Override
    public long incr(String key) {
        return redisClient.incr(key);
    }

    @Override
    public long expire(String key, int second) {
        return redisClient.expire(key, second);
    }

    @Override
    public long ttl(String key) {
        return redisClient.ttl(key);
    }

    @Override
    public long del(String key) {
        return redisClient.del(key);
    }

    @Override
    public long hdel(String hkey, String key) {
        return redisClient.hdel(hkey, key);
    }
}

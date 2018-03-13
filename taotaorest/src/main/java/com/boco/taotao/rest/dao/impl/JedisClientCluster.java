package com.boco.taotao.rest.dao.impl;

import com.boco.taotao.rest.dao.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;

/**
 * 用于集群的redis的对象
 * Created by Sheamus on 2018/2/10.
 */
public class JedisClientCluster implements JedisClient {
    @Autowired
    private JedisCluster jedisClientCluster;

    @Override
    public String get(String key) {
        return jedisClientCluster.get(key);
    }

    @Override
    public String set(String key, String value) {
        return jedisClientCluster.set(key, value);
    }

    @Override
    public String hget(String hkey, String key) {
        return jedisClientCluster.hget(hkey, key);
    }

    @Override
    public long hset(String hkey, String key, String value) {
        return jedisClientCluster.hset(hkey, key, value);
    }

    @Override
    public long incr(String key) {
        return jedisClientCluster.incr(key);
    }

    @Override
    public long expire(String key, int second) {
        return jedisClientCluster.expire(key, second);
    }

    @Override
    public long ttl(String key) {
        return jedisClientCluster.ttl(key);
    }

    @Override
    public long del(String key) {
        return jedisClientCluster.del(key);
    }

    @Override
    public long hdel(String hkey, String key) {
        return jedisClientCluster.hdel(hkey, key);
    }
}

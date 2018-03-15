package com.boco.taotao.sso.service.impl;

import com.boco.taotao.mapper.TbUserMapper;
import com.boco.taotao.pojo.TbUser;
import com.boco.taotao.pojo.TbUserExample;
import com.boco.taotao.sso.dao.JedisClient;
import com.boco.taotao.sso.service.UserService;
import com.boco.taotao.vo.JsonUtils;
import com.boco.taotao.vo.TaotaoResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by Sheamus on 2018/3/15.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;
    @Autowired
    private JedisClient jedisClient;
    @Value(value = "#{propertyConfigure['REDIS_USER_SESSION_KEY']}")
    private String REDIS_USER_SESSION_KEY;
    @Value(value = "#{propertyConfigure['SSO_SESSION_EXPIRE']}")
    private Integer SSO_SESSION_EXPIRE;

    @Override
    public TaotaoResult checkData(String content, Integer type) {
        //对数据进行校验，1,2,3 分别代表username、phone、email
        TbUserExample userExample = new TbUserExample();
        TbUserExample.Criteria criteria = userExample.createCriteria();
        switch (type) {
            case 1:
                criteria.andUsernameEqualTo(content);
                break;
            case 2:
                criteria.andPhoneEqualTo(content);
                break;
            case 3:
                criteria.andEmailEqualTo(content);
                break;
            default:

                break;
        }
        List<TbUser> tbUsers = userMapper.selectByExample(userExample);
        if(tbUsers == null || tbUsers.size() == 0) {
            return TaotaoResult.ok(true);
        }
        return TaotaoResult.ok(false);
    }

    @Override
    public TaotaoResult createUser(TbUser user) {
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //Spring的MD5加密
        String md5DigestAsHex = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5DigestAsHex);
        userMapper.insert(user);
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult userLogin(String username, String password) {
        //注意这里只进行用户名比对
        TbUserExample userExample = new TbUserExample();
        TbUserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> users = userMapper.selectByExample(userExample);

        //判断该用户名是否存在
        if(users == null || users.size() == 0) {
            return TaotaoResult.build(400,"用户名或密码错误！");
        }
        //用户名存在，把密码取出来和给的密码进行比对
        TbUser user = users.get(0);
        //使用MD5加密
        String userPassword = user.getPassword();
        if(!StringUtils.isBlank(userPassword)) {
            String digestAsHex = DigestUtils.md5DigestAsHex(password.getBytes());
            if(digestAsHex.equals(userPassword)) {
                //密码正确
                //生成token，并且入redis缓存
                String token = UUID.randomUUID().toString();
                String key = REDIS_USER_SESSION_KEY + ":" + token;
                //为了安全起见，把密码从对象中去除然后序列化
                user.setPassword(null);
                jedisClient.set(key, JsonUtils.objectToJson(user));
                //设置session的过期时间
                jedisClient.expire(key,SSO_SESSION_EXPIRE);
                return TaotaoResult.ok(token);
            }
        }
        return TaotaoResult.build(400,"用户名或密码错误！");
    }

    @Override
    public TaotaoResult getUserByToken(String token) {
        String key = REDIS_USER_SESSION_KEY + ":" + token;
        String json = jedisClient.get(key);
        //判断json是否存在
        if(StringUtils.isBlank(json)) {
            return TaotaoResult.build(400,"session过期请重新登录");
        }
        //重置过期时间
        jedisClient.expire(key,SSO_SESSION_EXPIRE);
        TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
        return TaotaoResult.ok(user);
    }

    @Override
    public TaotaoResult signOut(String token) {
        String key = REDIS_USER_SESSION_KEY + ":" + token;
        jedisClient.del(key);
        return TaotaoResult.ok();
    }
}

package com.boco.taotao.sso.service.impl;

import com.boco.taotao.mapper.TbUserMapper;
import com.boco.taotao.pojo.TbUser;
import com.boco.taotao.pojo.TbUserExample;
import com.boco.taotao.sso.service.UserService;
import com.boco.taotao.vo.TaotaoResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Sheamus on 2018/3/15.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;

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
}

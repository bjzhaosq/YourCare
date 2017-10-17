package com.lawer.dao;

import com.lawer.domain.WechatUser;

public interface WechatUserDao extends BaseDao<WechatUser>{

    public WechatUser findWechatUserByOpenid(String openid);

}

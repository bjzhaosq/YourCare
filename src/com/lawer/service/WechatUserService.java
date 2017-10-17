package com.lawer.service;

import com.lawer.domain.WechatUser;

public interface WechatUserService {

    public WechatUser findWechatUserByOpenid(String openid);

    public void saveWechatUser(WechatUser wechatUser);

    public void updateWechatUser(WechatUser wechatUser);
}

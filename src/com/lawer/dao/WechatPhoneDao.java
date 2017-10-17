package com.lawer.dao;

import com.lawer.domain.WechatPhone;

import java.util.List;

public interface WechatPhoneDao extends BaseDao<WechatPhone>{

    public List<WechatPhone> findWechatPhone();

    public WechatPhone findWechatPhoneByName(String name);
}

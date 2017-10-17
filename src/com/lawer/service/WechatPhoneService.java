package com.lawer.service;

import com.lawer.domain.WechatPhone;

import java.util.List;

public interface WechatPhoneService {

    public void saveWechatPhone(WechatPhone wechatPhone);

    public void saveWechatPhone(List<WechatPhone> list);

    public List<WechatPhone> findWechatPhone();

    public WechatPhone findWechatPhoneByName(String name);

}

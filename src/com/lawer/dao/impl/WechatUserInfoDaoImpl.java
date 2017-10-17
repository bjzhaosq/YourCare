package com.lawer.dao.impl;

import com.lawer.dao.WechatUserInfoDao;
import com.lawer.domain.WechatUserInfo;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository(value = "wechatUserInfoDao")
public class WechatUserInfoDaoImpl extends ObjectDaoImpl<WechatUserInfo> implements WechatUserInfoDao {

	private static Logger logger = Logger.getLogger(WechatUserInfoDaoImpl.class);

}

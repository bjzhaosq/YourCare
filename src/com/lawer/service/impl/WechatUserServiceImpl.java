package com.lawer.service.impl;

import com.lawer.dao.WechatUserDao;
import com.lawer.domain.WechatUser;
import com.lawer.service.WechatUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "WechatUserService")
@Transactional
public class WechatUserServiceImpl extends BaseServiceImpl implements WechatUserService {

	private static Logger logger = Logger.getLogger(WechatUserServiceImpl.class);

	@Autowired
	private WechatUserDao wechatUserDao;

	@Override
	public WechatUser findWechatUserByOpenid(String openid) {
		return wechatUserDao.findWechatUserByOpenid(openid);
	}

	@Override
	public void saveWechatUser(WechatUser wechatUser) {
		wechatUserDao.save(wechatUser);
	}

	@Override
	public void updateWechatUser(WechatUser wechatUser) {
		wechatUserDao.update(wechatUser);
	}
}

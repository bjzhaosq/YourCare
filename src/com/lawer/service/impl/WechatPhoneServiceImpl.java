package com.lawer.service.impl;

import com.lawer.dao.WechatPhoneDao;
import com.lawer.domain.WechatPhone;
import com.lawer.service.WechatPhoneService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(value = "wechatPhoneService")
@Transactional
public class WechatPhoneServiceImpl extends BaseServiceImpl implements WechatPhoneService {

	private static Logger logger = Logger.getLogger(WechatPhoneServiceImpl.class);

	@Autowired
	private WechatPhoneDao wechatPhoneDao;

	@Override
	public void saveWechatPhone(WechatPhone wechatPhone) {
		wechatPhoneDao.save(wechatPhone);
	}

	@Override
	public void saveWechatPhone(List<WechatPhone> list) {
		wechatPhoneDao.save(list);
	}

	@Override
	public List<WechatPhone> findWechatPhone() {
		return wechatPhoneDao.findWechatPhone();
	}

	@Override
	public WechatPhone findWechatPhoneByName(String name) {
		return wechatPhoneDao.findWechatPhoneByName(name);
	}


}

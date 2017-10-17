package com.lawer.service.impl;

import com.lawer.service.WechatUserInfoService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "wechatUserInfoService")
@Transactional
public class WechatUserInfoServiceImpl extends BaseServiceImpl implements WechatUserInfoService {

	private static Logger logger = Logger.getLogger(WechatUserInfoServiceImpl.class);


}

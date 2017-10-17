package com.lawer.service.impl;

import com.lawer.service.WechatTodoTypeService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "wechatTodoTypeService")
@Transactional
public class WechatTodoTypeServiceImpl extends BaseServiceImpl implements WechatTodoTypeService {

	private static Logger logger = Logger.getLogger(WechatTodoTypeServiceImpl.class);


}

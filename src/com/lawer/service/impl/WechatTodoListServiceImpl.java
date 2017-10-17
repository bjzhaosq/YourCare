package com.lawer.service.impl;

import com.lawer.service.WechatTodoListService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service(value = "wechatTodoListService")
@Transactional
public class WechatTodoListServiceImpl extends BaseServiceImpl implements WechatTodoListService {

	private static Logger logger = Logger.getLogger(WechatTodoListServiceImpl.class);


}

package com.lawer.dao.impl;

import com.lawer.dao.WechatTodoTypeDao;
import com.lawer.domain.WechatTodoType;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository(value = "wechatTodoTypeDao")
public class WechatTodoTypeDaoImpl extends ObjectDaoImpl<WechatTodoType> implements WechatTodoTypeDao {

	private static Logger logger = Logger.getLogger(WechatTodoTypeDaoImpl.class);

}

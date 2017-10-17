package com.lawer.dao.impl;

import com.lawer.dao.WechatTodoListDao;
import com.lawer.domain.WechatTodoList;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

@Repository(value = "wechatTodoListDao")
public class WechatTodoListDaoImpl extends ObjectDaoImpl<WechatTodoList> implements WechatTodoListDao {

	private static Logger logger = Logger.getLogger(WechatTodoListDaoImpl.class);
}
